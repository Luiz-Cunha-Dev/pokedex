package com.example.pokedex.model;

import java.util.List;

public class PokemonSpecies {
    private String name;
    private String url;
    private Integer base_happiness;
    private Integer capture_rate;
    private Color color;
    private List<EggGroup> egg_groups;
    private EvolutionChain evolution_chain;
    private Object evolves_from_species;
    private List<FlavorTextEntry> flavor_text_entries;

    // Getters e setters
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

    public Integer getBaseHappiness() {
        return base_happiness;
    }

    public void setBaseHappiness(Integer base_happiness) {
        this.base_happiness = base_happiness;
    }

    public Integer getCaptureRate() {
        return capture_rate;
    }

    public void setCaptureRate(Integer capture_rate) {
        this.capture_rate = capture_rate;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public List<EggGroup> getEggGroups() {
        return egg_groups;
    }

    public void setEggGroups(List<EggGroup> egg_groups) {
        this.egg_groups = egg_groups;
    }

    public EvolutionChain getEvolutionChain() {
        return evolution_chain;
    }

    public void setEvolutionChain(EvolutionChain evolution_chain) {
        this.evolution_chain = evolution_chain;
    }

    public Object getEvolvesFromSpecies() {
        return evolves_from_species;
    }

    public void setEvolvesFromSpecies(Object evolves_from_species) {
        this.evolves_from_species = evolves_from_species;
    }

    public List<FlavorTextEntry> getFlavorTextEntries() {
        return flavor_text_entries;
    }

    public void setFlavorTextEntries(List<FlavorTextEntry> flavor_text_entries) {
        this.flavor_text_entries = flavor_text_entries;
    }

    // Classe interna para a cor do Pokémon
    public static class Color {
        private String name;
        private String url;

        // Getters e setters
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

    // Classe interna para os grupos de ovos
    public static class EggGroup {
        private String name;
        private String url;

        // Getters e setters
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

    // Classe interna para a cadeia de evolução
    public static class EvolutionChain {
        private String url;

        // Getter e setter
        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getId() {
            String[] urlParts = url.split("/");
            return Integer.parseInt(urlParts[urlParts.length - 1]);
        }
    }

    // Classe interna para as entradas de descrição
    public static class FlavorTextEntry {
        private String flavor_text;
        private Language language;
        private GameVersion version;

        // Getters e setters
        public String getFlavorText() {
            return flavor_text;
        }

        public void setFlavorText(String flavor_text) {
            this.flavor_text = flavor_text;
        }

        public Language getLanguage() {
            return language;
        }

        public void setLanguage(Language language) {
            this.language = language;
        }

        public GameVersion getVersion() {
            return version;
        }

        public void setVersion(GameVersion version) {
            this.version = version;
        }
    }

    // Classe interna para o idioma
    public static class Language {
        private String name;
        private String url;

        // Getters e setters
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

    // Classe interna para a versão do jogo
    public static class GameVersion {
        private String name;
        private String url;

        // Getter e setter
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
