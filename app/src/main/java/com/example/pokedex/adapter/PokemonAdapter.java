package com.example.pokedex.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pokedex.R;
import com.example.pokedex.model.PokemonFormResponse;
import com.example.pokedex.PokemonDetailActivity;
import com.example.pokedex.utils.PokemonTypeColors;

import java.util.ArrayList;
import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {

    private List<PokemonFormResponse> pokemonFormList = new ArrayList<>();
    private static final PokemonTypeColors pokemonTypeColors = new PokemonTypeColors();

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_card, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        PokemonFormResponse pokemonForm = pokemonFormList.get(position);
        holder.bind(pokemonForm);
    }

    @Override
    public int getItemCount() {
        return pokemonFormList.size();
    }

    public void updatePokemonList(List<PokemonFormResponse> newPokemonFormList) {
        pokemonFormList.clear();
        pokemonFormList.addAll(newPokemonFormList);
        notifyDataSetChanged();
    }

    static class PokemonViewHolder extends RecyclerView.ViewHolder {

        private final TextView pokemonNumber;
        private final TextView pokemonName;
        private final TextView pokemonType1;
        private final TextView pokemonType2;
        private final ImageView pokemonImage;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            pokemonNumber = itemView.findViewById(R.id.pokemon_number);
            pokemonName = itemView.findViewById(R.id.pokemon_name_detail);
            pokemonType1 = itemView.findViewById(R.id.pokemon_type1);
            pokemonType2 = itemView.findViewById(R.id.pokemon_type2);
            pokemonImage = itemView.findViewById(R.id.pokemon_image);

            itemView.setOnClickListener(v -> {
                Context context = v.getContext();
                Intent intent = new Intent(context, PokemonDetailActivity.class);
                intent.putExtra("POKEMON_NAME", pokemonName.getText().toString());
                context.startActivity(intent);
            });
        }

        public void bind(PokemonFormResponse pokemonForm) {
            pokemonNumber.setText("Nº " + pokemonForm.getId());
            pokemonName.setText(pokemonForm.getName());

            List<PokemonFormResponse.Type> types = pokemonForm.getTypes();
            if (types.size() == 1) {
                setType(pokemonType1, types.get(0));
                pokemonType2.setVisibility(View.INVISIBLE);
            } else {
                setType(pokemonType1, types.get(0));
                setType(pokemonType2, types.get(1));
                pokemonType2.setVisibility(View.VISIBLE);
            }

            String imageUrl = pokemonForm.getSprites().getFrontDefault();
            Glide.with(itemView.getContext())
                    .load(imageUrl)
                    .into(pokemonImage);
        }

        private void setType(TextView textView, PokemonFormResponse.Type type) {
            String typeName = type.getType().getName();
            textView.setText(typeName);
            textView.setTextColor(Color.parseColor(pokemonTypeColors.getTypeColors().get(typeName)[1]));
            textView.setBackgroundColor(Color.parseColor(pokemonTypeColors.getTypeColors().get(typeName)[0]));
            textView.setVisibility(View.VISIBLE);
        }
    }
}