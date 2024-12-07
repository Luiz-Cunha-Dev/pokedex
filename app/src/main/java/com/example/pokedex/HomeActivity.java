package com.example.pokedex;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Button enterButton;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        enterButton = findViewById(R.id.enter_button);
        mediaPlayer = MediaPlayer.create(this, R.raw.pokemon_go_title);
        mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(1.4f));
        mediaPlayer.start();
    }

    public void openPokemonListActivity(View view) {
        enterButton.setEnabled(false);
        enterButton.setText("Loading...");

        handler.postDelayed(() -> {
            enterButton.setText("Welcome :)");
            handler.postDelayed(() -> {
                Intent intent = new Intent(this, PokemonListActivity.class);
                startActivity(intent);
                enterButton.setEnabled(true);
                enterButton.setText("Enter");
            }, 1400);
        }, 600);
    }
}