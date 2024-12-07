package com.example.pokedex.adapter;

import android.content.Context;
import android.content.Intent;
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
import android.graphics.Color;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {

    private List<PokemonFormResponse> pokemonFormList = new ArrayList<>();
    private static PokemonTypeColors pokemonTypeColors = new PokemonTypeColors();

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

        private TextView pokemonNumber;
        private TextView pokemonName;
        private TextView pokemonType1, pokemonType2;
        private ImageView pokemonImage;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            pokemonNumber = itemView.findViewById(R.id.pokemon_number);
            pokemonName = itemView.findViewById(R.id.pokemon_name_detail);
            pokemonType1 = itemView.findViewById(R.id.pokemon_type1);
            pokemonType2 = itemView.findViewById(R.id.pokemon_type2);
            pokemonImage = itemView.findViewById(R.id.pokemon_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, PokemonDetailActivity.class);
                    intent.putExtra("POKEMON_NAME", pokemonName.getText().toString());
                    context.startActivity(intent);
                }
            });
        }

        public void bind(PokemonFormResponse pokemonForm) {
            // Exibindo o número do Pokémon
            pokemonNumber.setText("Nº " + pokemonForm.getId());

            pokemonName.setText(pokemonForm.getName());

            // Exibindo os tipos do Pokémon
            List<PokemonFormResponse.Type> types = pokemonForm.getTypes();

            if (types.size() == 1) {
                pokemonType1.setText(types.get(0).getType().getName());
                pokemonType1.setTextColor(Color.parseColor(pokemonTypeColors.getTypeColors().get(types.get(0).getType().getName())[1]));
                pokemonType1.setBackgroundColor(Color.parseColor(pokemonTypeColors.getTypeColors().get(types.get(0).getType().getName())[0]));
                pokemonType1.setVisibility(View.VISIBLE);
                pokemonType2.setVisibility(View.INVISIBLE);
            } else {
                pokemonType1.setText(types.get(0).getType().getName());
                pokemonType1.setTextColor(Color.parseColor(pokemonTypeColors.getTypeColors().get(types.get(0).getType().getName())[1]));
                pokemonType1.setBackgroundColor(Color.parseColor(pokemonTypeColors.getTypeColors().get(types.get(0).getType().getName())[0]));
                pokemonType2.setText(types.get(1).getType().getName());
                pokemonType2.setTextColor(Color.parseColor(pokemonTypeColors.getTypeColors().get(types.get(1).getType().getName())[1]));
                pokemonType2.setBackgroundColor(Color.parseColor(pokemonTypeColors.getTypeColors().get(types.get(1).getType().getName())[0]));
                pokemonType1.setVisibility(View.VISIBLE);
                pokemonType2.setVisibility(View.VISIBLE);
            }

            // Usando Glide para carregar a imagem
            String imageUrl = pokemonForm.getSprites().getFrontDefault();
            System.out.println(imageUrl);
            Glide.with(itemView.getContext())
                    .load(imageUrl)
                    .into(pokemonImage);
        }
    }
}