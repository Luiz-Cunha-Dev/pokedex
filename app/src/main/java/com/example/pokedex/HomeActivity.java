package com.example.pokedex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pokedex.api.Gemini;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
    }

    public void openPokemonListActivity(View view) {
        Gemini gemini = new Gemini();
        Intent intent = new Intent(this, PokemonListActivity.class);
        startActivity(intent);
    }
}
