package com.example.pokedex.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PokemonFormResponse {
    private String formName;
    private int formOrder;
    private int id;
    private boolean isBattleOnly;
    private boolean isDefault;
    private boolean isMega;
    private String name;
    private int order;
    private Pokemon pokemon;
    private Sprites sprites;
    private List<Type> types;

    // Getters e Setters
    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public int getFormOrder() {
        return formOrder;
    }

    public void setFormOrder(int formOrder) {
        this.formOrder = formOrder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isBattleOnly() {
        return isBattleOnly;
    }

    public void setBattleOnly(boolean battleOnly) {
        isBattleOnly = battleOnly;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public boolean isMega() {
        return isMega;
    }

    public void setMega(boolean mega) {
        isMega = mega;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    // Classes internas para as subestruturas

    public static class Sprites {
        @SerializedName("back_default")
        private String backDefault;
        @SerializedName("back_female")
        private String backFemale;
        @SerializedName("back_shiny")
        private String backShiny;
        @SerializedName("back_shiny_female")
        private String backShinyFemale;
        @SerializedName("front_default")
        private String frontDefault;
        @SerializedName("front_female")
        private String frontFemale;
        @SerializedName("front_shiny")
        private String frontShiny;
        @SerializedName("front_shiny_female")
        private String frontShinyFemale;

        // Getters e Setters
        public String getBackDefault() {
            return backDefault;
        }

        public void setBackDefault(String backDefault) {
            this.backDefault = backDefault;
        }

        public String getBackFemale() {
            return backFemale;
        }

        public void setBackFemale(String backFemale) {
            this.backFemale = backFemale;
        }

        public String getBackShiny() {
            return backShiny;
        }

        public void setBackShiny(String backShiny) {
            this.backShiny = backShiny;
        }

        public String getBackShinyFemale() {
            return backShinyFemale;
        }

        public void setBackShinyFemale(String backShinyFemale) {
            this.backShinyFemale = backShinyFemale;
        }

        public String getFrontDefault() {
            return frontDefault;
        }

        public void setFrontDefault(String frontDefault) {
            this.frontDefault = frontDefault;
        }

        public String getFrontFemale() {
            return frontFemale;
        }

        public void setFrontFemale(String frontFemale) {
            this.frontFemale = frontFemale;
        }

        public String getFrontShiny() {
            return frontShiny;
        }

        public void setFrontShiny(String frontShiny) {
            this.frontShiny = frontShiny;
        }

        public String getFrontShinyFemale() {
            return frontShinyFemale;
        }

        public void setFrontShinyFemale(String frontShinyFemale) {
            this.frontShinyFemale = frontShinyFemale;
        }
    }

    public static class Type {
        private int slot;
        private PokemonType type;

        // Getters e Setters
        public int getSlot() {
            return slot;
        }

        public void setSlot(int slot) {
            this.slot = slot;
        }

        public PokemonType getType() {
            return type;
        }

        public void setType(PokemonType type) {
            this.type = type;
        }

        public static class PokemonType {
            private String name;
            private String url;

            // Getters e Setters
            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }

    public static class Pokemon {
        private String name;
        private String url;

        // Getters e Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
