package com.example.pokedex;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pokedex.adapter.PokemonAdapter;
import com.example.pokedex.api.Gemini;
import com.example.pokedex.api.PokemonApi;
import com.example.pokedex.model.PokemonFormResponse;
import com.example.pokedex.model.PokemonListResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText inputSearch;
    private ImageButton searchButton;
    private PokemonAdapter adapter;
    private List<String> pokemonNames = new ArrayList<>();
    private List<PokemonFormResponse> pokemonFormList = new ArrayList<>();
    private Uri selectedImageUri;
    private Uri photoUri;
    private ActivityResultLauncher<Intent> selectImageLauncher;
    private ActivityResultLauncher<Uri> captureImageLauncher;
    private ImageButton photoGalleryButton, photoCameraButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon_list);

        initializeViews();
        setupRecyclerView();
        setupActivityResultLaunchers();
        setupSearchAction();
        fetchPokemonList(0, 20);
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.pokemon_list);
        inputSearch = findViewById(R.id.input_search);
        searchButton = findViewById(R.id.search_button);
        photoCameraButton = findViewById(R.id.photo_camera_button);
        photoGalleryButton = findViewById(R.id.photo_gallery_button);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PokemonAdapter();
        recyclerView.setAdapter(adapter);
        addScrollListener();
    }

    private void setupActivityResultLaunchers() {
        selectImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        Glide.with(this)
                                .asGif()
                                .load(R.drawable.loading)
                                .into(photoGalleryButton);
                        sendImageToGemini(selectedImageUri);
                    }
                });

        captureImageLauncher = registerForActivityResult(
                new ActivityResultContracts.TakePicture(),
                result -> {
                    if (result) {
                        Glide.with(this)
                                .asGif()
                                .load(R.drawable.loading)
                                .into(photoCameraButton);
                        sendImageToGemini(photoUri);
                    }
                });
    }

    private void setupSearchAction() {
        inputSearch.setOnEditorActionListener((textView, actionId, event) -> {
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
                    || actionId == android.view.inputmethod.EditorInfo.IME_ACTION_DONE
                    || (event != null && event.getKeyCode() == android.view.KeyEvent.KEYCODE_ENTER && event.getAction() == android.view.KeyEvent.ACTION_DOWN)) {
                searchPokemonByNameOrId(searchButton);
                hideKeyboard(inputSearch);
                inputSearch.clearFocus();
                return true;
            }
            return false;
        });
    }

    private void sendImageToGemini(Uri imageUri) {
        Gemini gemini = new Gemini();
        gemini.identifyPokemonInImage(imageUri, this, new Gemini.PokemonIdentificationCallback() {
            @Override
            public void onSuccess(String pokemonName) {
                runOnUiThread(() -> {
                    inputSearch.setText(pokemonName.trim());
                    searchPokemonByNameOrId(searchButton);
                    resetImageButtons();
                });
            }

            @Override
            public void onFailure(Throwable t) {
                runOnUiThread(() -> Toast.makeText(PokemonListActivity.this, "Failed to identify Pokémon: " + t.getMessage(), Toast.LENGTH_SHORT).show());
                resetImageButtons();
            }
        });
    }

    private void resetImageButtons() {
        photoGalleryButton.setImageResource(R.drawable.baseline_crop_original_24);
        photoCameraButton.setImageResource(R.drawable.baseline_add_a_photo_24);
    }

    private void addScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) {
                    fetchPokemonList(pokemonFormList.size(), 20);
                }
            }
        });
    }

    private void fetchPokemonList(int offset, int limit) {
        PokemonApi.getPokemonList(offset, limit).enqueue(new Callback<PokemonListResponse>() {
            @Override
            public void onResponse(Call<PokemonListResponse> call, Response<PokemonListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    pokemonNames.clear();
                    for (PokemonListResponse.PokemonSummary pokemonSummary : response.body().getResults()) {
                        pokemonNames.add(pokemonSummary.getName());
                        fetchPokemonForm(pokemonSummary.getName());
                    }
                } else {
                    Toast.makeText(PokemonListActivity.this, "Erro ao carregar dados", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PokemonListResponse> call, Throwable t) {
                Log.e("PokemonApi", "Erro: " + t.getMessage());
                Toast.makeText(PokemonListActivity.this, "Falha na conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchPokemonForm(String name) {
        PokemonApi.getPokemonForm(name).enqueue(new Callback<PokemonFormResponse>() {
            @Override
            public void onResponse(Call<PokemonFormResponse> call, Response<PokemonFormResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PokemonFormResponse pokemonForm = response.body();
                    if (pokemonFormList.stream().noneMatch(existingPokemon -> existingPokemon.getId() == pokemonForm.getId())) {
                        pokemonFormList.add(pokemonForm);
                        pokemonFormList.sort((pokemon1, pokemon2) -> pokemon1.getId() - pokemon2.getId());
                        adapter.updatePokemonList(pokemonFormList);
                    }
                } else {
                    Log.e("PokemonApi", "Erro ao buscar detalhes do Pokémon: " + name);
                }
            }

            @Override
            public void onFailure(Call<PokemonFormResponse> call, Throwable t) {
                Log.e("PokemonApi", "Erro: " + t.getMessage());
                Toast.makeText(PokemonListActivity.this, "Falha na conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void searchPokemonByNameOrId(View view) {
        String search = inputSearch.getText().toString().toLowerCase();
        if (search.isEmpty()) {
            resetSearch(view);
        } else {
            removeScrollListener();
            PokemonApi.getPokemonForm(search).enqueue(new Callback<PokemonFormResponse>() {
                @Override
                public void onResponse(Call<PokemonFormResponse> call, Response<PokemonFormResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        PokemonFormResponse pokemonForm = response.body();
                        pokemonFormList.clear();
                        pokemonNames.clear();
                        pokemonFormList.add(pokemonForm);
                        adapter.updatePokemonList(pokemonFormList);
                    } else {
                        Log.e("PokemonApi", "Erro ao buscar detalhes do Pokémon: " + search);
                    }
                }

                @Override
                public void onFailure(Call<PokemonFormResponse> call, Throwable t) {
                    Log.e("PokemonApi", "Erro: " + t.getMessage());
                }
            });
        }
    }

    public void resetSearch(View view) {
        pokemonFormList.clear();
        pokemonNames.clear();
        inputSearch.setText("");
        fetchPokemonList(0, 20);
        addScrollListener();
    }

    public void getImageFromGallery(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        selectImageLauncher.launch(intent);
    }

    public void captureImage(View view) {
        photoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
        captureImageLauncher.launch(photoUri);
    }

    private void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void removeScrollListener() {
        recyclerView.clearOnScrollListeners();
    }
}