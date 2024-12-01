package com.example.pokedex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
    }

    public void openPokemonListActivity(View view) {
        Intent intent = new Intent(this, PokemonListActivity.class);
        startActivity(intent);
    }
}
