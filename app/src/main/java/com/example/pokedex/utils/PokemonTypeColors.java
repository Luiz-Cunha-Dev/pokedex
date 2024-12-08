package com.example.pokedex.utils;

import java.util.HashMap;
import java.util.Map;

public class PokemonTypeColors {

    public static Map<String, String[]> getTypeColors() {
        Map<String, String[]> typeColors = new HashMap<>();

        typeColors.put("normal", new String[]{"#A8A878", "#000000"});
        typeColors.put("fire", new String[]{"#F08030", "#FFFFFF"});
        typeColors.put("water", new String[]{"#6890F0", "#FFFFFF"});
        typeColors.put("electric", new String[]{"#F8D030", "#000000"});
        typeColors.put("grass", new String[]{"#78C850", "#FFFFFF"});
        typeColors.put("ice", new String[]{"#98D8D8", "#000000"});
        typeColors.put("fighting", new String[]{"#C03028", "#FFFFFF"});
        typeColors.put("poison", new String[]{"#A040A0", "#FFFFFF"});
        typeColors.put("ground", new String[]{"#E0C068", "#000000"});
        typeColors.put("flying", new String[]{"#A890F0", "#000000"});
        typeColors.put("psychic", new String[]{"#F85888", "#FFFFFF"});
        typeColors.put("bug", new String[]{"#A8B820", "#000000"});
        typeColors.put("rock", new String[]{"#B8A038", "#000000"});
        typeColors.put("ghost", new String[]{"#705898", "#FFFFFF"});
        typeColors.put("dragon", new String[]{"#7038F8", "#FFFFFF"});
        typeColors.put("dark", new String[]{"#705848", "#FFFFFF"});
        typeColors.put("steel", new String[]{"#B8B8D0", "#000000"});
        typeColors.put("fairy", new String[]{"#EE99AC", "#000000"});

        return typeColors;
    }
}
