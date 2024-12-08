package com.example.pokedex.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.pokedex.BuildConfig;
import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Gemini {

    private final GenerativeModelFutures model;

    public Gemini() {
        GenerativeModel gm = new GenerativeModel("gemini-1.5-flash", BuildConfig.GEMINI_API_KEY);
        this.model = GenerativeModelFutures.from(gm);
    }

    public void identifyPokemonInImage(Uri imageUri, Context context, PokemonIdentificationCallback callback) {
        try {
            Bitmap image = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
            Content content = new Content.Builder()
                    .addText("Identify the Pokémon in the image. Your answer should only contain the name of the identified Pokémon, nothing else.")
                    .addImage(image)
                    .build();

            Executor executor = Executors.newSingleThreadExecutor();
            ListenableFuture<GenerateContentResponse> response = model.generateContent(content);

            Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
                @Override
                public void onSuccess(GenerateContentResponse result) {
                    callback.onSuccess(result.getText());
                }

                @Override
                public void onFailure(Throwable t) {
                    callback.onFailure(t);
                }
            }, executor);
        } catch (IOException e) {
            callback.onFailure(e);
        }
    }

    public interface PokemonIdentificationCallback {
        void onSuccess(String pokemonName);
        void onFailure(Throwable t);
    }
}