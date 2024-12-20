package com.example.pokedex;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.pokedex.api.PokemonApi;
import com.example.pokedex.model.Pokemon;
import com.example.pokedex.model.PokemonEvolutionChain;
import com.example.pokedex.model.PokemonSpecies;
import com.example.pokedex.utils.PokemonTypeColors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.graphics.Color;

public class PokemonDetailActivity extends AppCompatActivity {

    private Pokemon pokemon;
    private TextView descriptionText;
    private ImageView pokemonEvolutionImageView1, pokemonEvolutionImageView2, pokemonEvolutionImageView3;
    private TextView pokemonEvolutionName1, pokemonEvolutionName2, pokemonEvolutionName3;
    private MediaPlayer pokemonSoundPlayer;
    private ImageView arrow1, arrow2;
    private static final PokemonTypeColors pokemonTypeColors = new PokemonTypeColors();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon_detail);

        initializeViews();
        String pokemonName = getIntent().getStringExtra("POKEMON_NAME");
        fetchPokemonDetails(pokemonName);
        fetchPokemonSpecies(pokemonName);
    }

    private void initializeViews() {
        descriptionText = findViewById(R.id.pokemon_description_detail);
        pokemonEvolutionImageView1 = findViewById(R.id.pokemon_image_evolution1);
        pokemonEvolutionImageView2 = findViewById(R.id.pokemon_image_evolution2);
        pokemonEvolutionImageView3 = findViewById(R.id.pokemon_image_evolution3);
        pokemonEvolutionName1 = findViewById(R.id.pokemon_name_evolution1);
        pokemonEvolutionName2 = findViewById(R.id.pokemon_name_evolution2);
        pokemonEvolutionName3 = findViewById(R.id.pokemon_name_evolution3);
        arrow1 = findViewById(R.id.arrow1);
        arrow2 = findViewById(R.id.arrow2);
    }

    private void fetchPokemonDetails(String pokemonName) {
        PokemonApi.getPokemonDetails(pokemonName).enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                if (response.isSuccessful()) {
                    pokemon = response.body();
                    updateUI();
                }
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                Log.e("POKEDEX", "Erro ao buscar detalhes do Pokémon", t);
            }
        });
    }

    private void fetchPokemonSpecies(String pokemonName) {
        PokemonApi.getPokemonSpecies(pokemonName).enqueue(new Callback<PokemonSpecies>() {
            @Override
            public void onResponse(Call<PokemonSpecies> call, Response<PokemonSpecies> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PokemonSpecies pokemonSpecies = response.body();
                    updateDescription(pokemonSpecies);
                    fetchPokemonEvolutionChain(pokemonSpecies.getEvolutionChain().getId());
                }
            }

            @Override
            public void onFailure(Call<PokemonSpecies> call, Throwable t) {
                Log.e("POKEDEX", "Erro ao buscar detalhes da espécie do Pokémon", t);
            }
        });
    }

    private void updateDescription(PokemonSpecies pokemonSpecies) {
        for (PokemonSpecies.FlavorTextEntry flavorTextEntry : pokemonSpecies.getFlavorTextEntries()) {
            if ("en".equals(flavorTextEntry.getLanguage().getName())) {
                String description = flavorTextEntry.getFlavorText().replaceAll("\n", "");
                descriptionText.setText(description);
                break;
            }
        }
    }

    private void fetchPokemonEvolutionChain(int id) {
        PokemonApi.getPokemonEvolutionChain(id).enqueue(new Callback<PokemonEvolutionChain>() {
            @Override
            public void onResponse(Call<PokemonEvolutionChain> call, Response<PokemonEvolutionChain> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<String> pokemonNames = new ArrayList<>();
                    extractPokemonNames(response.body().getChain(), pokemonNames);
                    updateEvolutionViews(pokemonNames);
                }
            }

            @Override
            public void onFailure(Call<PokemonEvolutionChain> call, Throwable t) {
                Log.e("POKEDEX", "Erro ao buscar cadeia de evolução do Pokémon", t);
            }
        });
    }

    private void extractPokemonNames(PokemonEvolutionChain.Chain chain, List<String> pokemonNames) {
        if (chain != null) {
            pokemonNames.add(chain.getSpecies().getName());
            extractEvolutions(chain, pokemonNames);
        }
    }

    private void extractEvolutions(PokemonEvolutionChain.Chain chain, List<String> pokemonNames) {
        if (chain.getEvolvesTo() != null && !chain.getEvolvesTo().isEmpty()) {
            for (PokemonEvolutionChain.Chain evolvesTo : chain.getEvolvesTo()) {
                pokemonNames.add(evolvesTo.getSpecies().getName());
                extractEvolutions(evolvesTo, pokemonNames);
            }
        }
    }

    private void updateEvolutionViews(List<String> pokemonNames) {
        List<ImageView> evolutionImageViews = List.of(pokemonEvolutionImageView1, pokemonEvolutionImageView2, pokemonEvolutionImageView3);
        List<TextView> evolutionNameViews = List.of(pokemonEvolutionName1, pokemonEvolutionName2, pokemonEvolutionName3);

        for (int i = 0; i < pokemonNames.size(); i++) {
            if (i < evolutionImageViews.size() && i < evolutionNameViews.size()) {
                updateEvolutionView(pokemonNames.get(i), evolutionImageViews.get(i), evolutionNameViews.get(i));
            }
        }

        adjustEvolutionViewVisibility(pokemonNames.size());
    }

    private void updateEvolutionView(String pokemonName, ImageView imageView, TextView textView) {
        PokemonApi.getPokemonDetails(pokemonName).enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Pokemon pokemonDetails = response.body();
                    textView.setText(pokemonName);
                    Glide.with(imageView.getContext()).load(pokemonDetails.getSprites().getFrontDefault()).into(imageView);
                } else {
                    Log.e("POKEDEX", "Erro ao carregar imagem para: " + pokemonName);
                }
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                Log.e("POKEDEX", "Erro ao buscar detalhes do Pokémon: " + pokemonName, t);
            }
        });
    }

    private void adjustEvolutionViewVisibility(int size) {
        if (size == 1) {
            pokemonEvolutionName2.setVisibility(View.GONE);
            pokemonEvolutionImageView2.setVisibility(View.GONE);
            pokemonEvolutionName3.setVisibility(View.GONE);
            pokemonEvolutionImageView3.setVisibility(View.GONE);
            arrow1.setVisibility(View.GONE);
            arrow2.setVisibility(View.GONE);
        } else if (size == 2) {
            pokemonEvolutionName3.setVisibility(View.GONE);
            pokemonEvolutionImageView3.setVisibility(View.GONE);
            arrow2.setVisibility(View.GONE);
        }
    }

    private void updateUI() {
        if (pokemon == null) return;

        TextView pokemonNameTextView = findViewById(R.id.pokemon_name_detail);
        TextView pokemonHeightTextView = findViewById(R.id.pokemon_height_detail);
        TextView pokemonWeightTextView = findViewById(R.id.pokemon_weight_detail);
        TextView pokemonType1TextView = findViewById(R.id.pokemon_type1_detail);
        TextView pokemonType2TextView = findViewById(R.id.pokemon_type2_detail);
        ImageView pokemonImageView = findViewById(R.id.pokemon_image_detail);
        TextView pokemonHp = findViewById(R.id.pokemon_hp_detail);
        TextView pokemonAttack = findViewById(R.id.pokemon_attack_detail);

        pokemonNameTextView.setText(pokemon.getName());
        pokemonHeightTextView.setText("Height: " + pokemon.getHeight() / 10.0 + "m");
        pokemonWeightTextView.setText("Weight: " + pokemon.getWeight() / 10.0 + "kg");

        updatePokemonTypeViews(pokemonType1TextView, pokemonType2TextView);
        updatePokemonImageView(pokemonImageView);
        updatePokemonStats(pokemonHp, pokemonAttack);
    }

    private void updatePokemonTypeViews(TextView pokemonType1TextView, TextView pokemonType2TextView) {
        if (!pokemon.getTypes().isEmpty()) {
            setPokemonTypeView(pokemonType1TextView, pokemon.getTypes().get(0).getTypeInfo().getName());
        } else {
            pokemonType1TextView.setVisibility(View.GONE);
        }

        if (pokemon.getTypes().size() > 1) {
            setPokemonTypeView(pokemonType2TextView, pokemon.getTypes().get(1).getTypeInfo().getName());
        } else {
            pokemonType2TextView.setVisibility(View.GONE);
        }
    }

    private void setPokemonTypeView(TextView textView, String typeName) {
        textView.setText(typeName);
        textView.setTextColor(Color.parseColor(pokemonTypeColors.getTypeColors().get(typeName)[1]));
        textView.setBackgroundColor(Color.parseColor(pokemonTypeColors.getTypeColors().get(typeName)[0]));
        textView.setVisibility(View.VISIBLE);
    }

    private void updatePokemonImageView(ImageView pokemonImageView) {
        String imageUrl = pokemon.getSprites().getFrontDefault();
        Glide.with(pokemonImageView.getContext()).load(imageUrl).into(pokemonImageView);
    }

    private void updatePokemonStats(TextView pokemonHp, TextView pokemonAttack) {
        pokemonHp.setText("HP: " + pokemon.getStats().get(0).getBaseStat());
        pokemonAttack.setText("Attack: " + pokemon.getStats().get(1).getBaseStat());
    }

    public void playPokemonSound(View view) {
        if (pokemon != null) {
            pokemonSoundPlayer = new MediaPlayer();
            try {
                pokemonSoundPlayer.setDataSource("https://raw.githubusercontent.com/PokeAPI/cries/main/cries/pokemon/latest/" + pokemon.getId() + ".ogg");
                pokemonSoundPlayer.prepare();
                pokemonSoundPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void back(View view) {
        finish();
    }
}