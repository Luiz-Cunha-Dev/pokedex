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
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

public class PokemonListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.OnScrollListener scrollListener;
    private EditText inputSearch;
    private ImageButton searchButton;
    private PokemonAdapter adapter;
    private List<String> pokemonNames = new ArrayList<>();
    private List<PokemonFormResponse> pokemonFormList = new ArrayList<>();
    private Uri selectedImageUri;
    private Uri photoUri;
    private ActivityResultLauncher<Intent> selectImageLauncher;
    private ActivityResultLauncher<Uri> captureImageLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon_list);

        recyclerView = findViewById(R.id.pokemon_list);
        inputSearch = findViewById(R.id.input_search);
        searchButton = findViewById(R.id.search_button);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new PokemonAdapter();
        recyclerView.setAdapter(adapter);

        fetchPokemonList(0, 20);
        addScrollListener();

        // Initialize ActivityResultLauncher
        selectImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        sendImageToGemini(selectedImageUri);
                    }
                });

        captureImageLauncher = registerForActivityResult(
                new ActivityResultContracts.TakePicture(),
                result -> {
                    if (result) {
                        sendImageToGemini(photoUri);
                    }
                });

        // Configurar o listener para o botão Enter do teclado
        inputSearch.setOnEditorActionListener((textView, actionId, event) -> {
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
                    || actionId == android.view.inputmethod.EditorInfo.IME_ACTION_DONE
                    || (event != null && event.getKeyCode() == android.view.KeyEvent.KEYCODE_ENTER && event.getAction() == android.view.KeyEvent.ACTION_DOWN)) {
                searchPokemonByNameOrId(searchButton);
                hideKeyboard(inputSearch);
                inputSearch.clearFocus();
                return true; // Indica que a ação foi tratada
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
                });
            }

            @Override
            public void onFailure(Throwable t) {
                runOnUiThread(() -> Toast.makeText(PokemonListActivity.this, "Failed to identify Pokémon: " + t.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });
    }


    private void setupUI(View view) {
        // Listener para toques fora do EditText
        if (!(view instanceof EditText)) {
            view.setOnTouchListener((v, event) -> {
                hideKeyboard(view);
                inputSearch.clearFocus();
                return false;
            });
        }

        // Itera por todas as views dentro do layout
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View child = ((ViewGroup) view).getChildAt(i);
                setupUI(child);
            }
        }
    }


    private void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    private void addScrollListener() {
        if (scrollListener == null) {
            scrollListener = new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (!recyclerView.canScrollVertically(1)) {
                        // Carrega mais 10 Pokémons
                        fetchPokemonList(pokemonFormList.size(), 20);
                    }
                }
            };
            recyclerView.addOnScrollListener(scrollListener);
        }
    }

    private  void removeScrollListener() {
        if (scrollListener != null) {
            recyclerView.removeOnScrollListener(scrollListener);
            scrollListener = null;
        }
    }

    private void fetchPokemonList(int offset, int limit) {
        PokemonApi.getPokemonList(offset, limit).enqueue(new Callback<PokemonListResponse>() {
            @Override
            public void onResponse(Call<PokemonListResponse> call, Response<PokemonListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Limpar lista de nomes antes de adicionar novos
                    pokemonNames.clear();

                    for (PokemonListResponse.PokemonSummary pokemonSummary : response.body().getResults()) {
                        pokemonNames.add(pokemonSummary.getName());
                        // Buscar os detalhes de cada Pokémon após adicionar seu nome
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

                    // Verificar se o Pokémon já está na lista
                    boolean alreadyExists = pokemonFormList.stream()
                            .anyMatch(existingPokemon -> existingPokemon.getId() == pokemonForm.getId());

                    if (!alreadyExists) {
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
            pokemonFormList.clear();
            pokemonNames.clear();
            fetchPokemonList(0, 20);

            addScrollListener();
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


    public void getImageFromGallery(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        selectImageLauncher.launch(intent);
    }

    public void captureImage(View view) {
        photoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
        captureImageLauncher.launch(photoUri);
    }
}
