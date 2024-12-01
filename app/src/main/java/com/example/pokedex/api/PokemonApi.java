package com.example.pokedex.api;

import com.example.pokedex.model.Pokemon;
import com.example.pokedex.model.PokemonEvolutionChain;
import com.example.pokedex.model.PokemonFormResponse;
import com.example.pokedex.model.PokemonListResponse;
import com.example.pokedex.model.PokemonSpecies;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class PokemonApi {

    private static final String BASE_URL = "https://pokeapi.co/api/v2/";

    private static final ApiService apiService;

    static {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    // Interface para os endpoints da API
    public interface ApiService {

        // Endpoint para obter a lista paginada de Pokémons
        @GET("pokemon")
        Call<PokemonListResponse> getPokemonList(
                @Query("offset") int offset,
                @Query("limit") int limit
        );

        // Endpoint para obter os detalhes de um Pokémon
        @GET("pokemon/{name}")
        Call<Pokemon> getPokemonDetails(
                @Path("name") String name
        );

        @GET("pokemon-form/{name}")
        Call<PokemonFormResponse> getPokemonForm(
                @Path("name") String name
        );

        @GET("pokemon-species/{name}")
        Call<PokemonSpecies> getPokemonSpecies(
                @Path("name") String name
        );

        @GET("evolution-chain/{id}")
        Call<PokemonEvolutionChain> getPokemonEvolutionChain(
                @Path("id") int id
        );
    }

    // Método para buscar a lista de Pokémons paginada
    public static Call<PokemonListResponse> getPokemonList(int offset, int limit) {
        return apiService.getPokemonList(offset, limit);
    }

    // Método para buscar os detalhes de um Pokémon
    public static Call<Pokemon> getPokemonDetails(String name) {
        return apiService.getPokemonDetails(name);
    }

    public static Call<PokemonFormResponse> getPokemonForm(String name) {
        return apiService.getPokemonForm(name);
    }

    public static Call<PokemonSpecies> getPokemonSpecies(String name) {
        return apiService.getPokemonSpecies(name);
    }

    public static Call<PokemonEvolutionChain> getPokemonEvolutionChain(int id) {
        return apiService.getPokemonEvolutionChain(id);
    }
}
