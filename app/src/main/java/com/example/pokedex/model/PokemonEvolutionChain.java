package com.example.pokedex.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PokemonEvolutionChain {

    private Integer id;

    @SerializedName("baby_trigger_item")
    private Object babyTriggerItem;

    private Chain chain;

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getBabyTriggerItem() {
        return babyTriggerItem;
    }

    public void setBabyTriggerItem(Object babyTriggerItem) {
        this.babyTriggerItem = babyTriggerItem;
    }

    public Chain getChain() {
        return chain;
    }

    public void setChain(Chain chain) {
        this.chain = chain;
    }

    public static class Chain {

        @SerializedName("evolution_details")
        private List<EvolutionDetail> evolutionDetails;

        @SerializedName("evolves_to")
        private List<Chain> evolvesTo;

        @SerializedName("is_baby")
        private Boolean isBaby;

        private Species species;

        // Getters e Setters
        public List<EvolutionDetail> getEvolutionDetails() {
            return evolutionDetails;
        }

        public void setEvolutionDetails(List<EvolutionDetail> evolutionDetails) {
            this.evolutionDetails = evolutionDetails;
        }

        public List<Chain> getEvolvesTo() {
            return evolvesTo;
        }

        public void setEvolvesTo(List<Chain> evolvesTo) {
            this.evolvesTo = evolvesTo;
        }

        public Boolean getIsBaby() {
            return isBaby;
        }

        public void setIsBaby(Boolean isBaby) {
            this.isBaby = isBaby;
        }

        public Species getSpecies() {
            return species;
        }

        public void setSpecies(Species species) {
            this.species = species;
        }

        public static class EvolutionDetail {

            private Object gender;

            @SerializedName("held_item")
            private Object heldItem;

            private Object item;

            @SerializedName("known_move")
            private Object knownMove;

            @SerializedName("known_move_type")
            private Object knownMoveType;

            private Object location;

            @SerializedName("min_affection")
            private Object minAffection;

            @SerializedName("min_beauty")
            private Object minBeauty;

            @SerializedName("min_happiness")
            private Object minHappiness;

            @SerializedName("min_level")
            private Integer minLevel;

            @SerializedName("needs_overworld_rain")
            private Boolean needsOverworldRain;

            @SerializedName("party_species")
            private Object partySpecies;

            @SerializedName("party_type")
            private Object partyType;

            @SerializedName("relative_physical_stats")
            private Object relativePhysicalStats;

            @SerializedName("time_of_day")
            private String timeOfDay;

            @SerializedName("trade_species")
            private Object tradeSpecies;


            private Trigger trigger;

            @SerializedName("turn_upside_down")
            private Boolean turnUpsideDown;

            public Object getGender() {
                return gender;
            }

            public void setGender(Object gender) {
                this.gender = gender;
            }

            public Object getHeldItem() {
                return heldItem;
            }

            public void setHeldItem(Object heldItem) {
                this.heldItem = heldItem;
            }

            public Object getItem() {
                return item;
            }

            public void setItem(Object item) {
                this.item = item;
            }

            public Object getKnownMove() {
                return knownMove;
            }

            public void setKnownMove(Object knownMove) {
                this.knownMove = knownMove;
            }

            public Object getKnownMoveType() {
                return knownMoveType;
            }

            public void setKnownMoveType(Object knownMoveType) {
                this.knownMoveType = knownMoveType;
            }

            public Object getLocation() {
                return location;
            }

            public void setLocation(Object location) {
                this.location = location;
            }

            public Object getMinAffection() {
                return minAffection;
            }

            public void setMinAffection(Object minAffection) {
                this.minAffection = minAffection;
            }

            public Object getMinBeauty() {
                return minBeauty;
            }

            public void setMinBeauty(Object minBeauty) {
                this.minBeauty = minBeauty;
            }

            public Object getMinHappiness() {
                return minHappiness;
            }

            public void setMinHappiness(Object minHappiness) {
                this.minHappiness = minHappiness;
            }

            public Integer getMinLevel() {
                return minLevel;
            }

            public void setMinLevel(Integer minLevel) {
                this.minLevel = minLevel;
            }

            public Boolean getNeedsOverworldRain() {
                return needsOverworldRain;
            }

            public void setNeedsOverworldRain(Boolean needsOverworldRain) {
                this.needsOverworldRain = needsOverworldRain;
            }

            public Object getPartySpecies() {
                return partySpecies;
            }

            public void setPartySpecies(Object partySpecies) {
                this.partySpecies = partySpecies;
            }

            public Object getPartyType() {
                return partyType;
            }

            public void setPartyType(Object partyType) {
                this.partyType = partyType;
            }

            public Object getRelativePhysicalStats() {
                return relativePhysicalStats;
            }

            public void setRelativePhysicalStats(Object relativePhysicalStats) {
                this.relativePhysicalStats = relativePhysicalStats;
            }

            public String getTimeOfDay() {
                return timeOfDay;
            }

            public void setTimeOfDay(String timeOfDay) {
                this.timeOfDay = timeOfDay;
            }

            public Object getTradeSpecies() {
                return tradeSpecies;
            }

            public void setTradeSpecies(Object tradeSpecies) {
                this.tradeSpecies = tradeSpecies;
            }

            public Trigger getTrigger() {
                return trigger;
            }

            public void setTrigger(Trigger trigger) {
                this.trigger = trigger;
            }

            public Boolean getTurnUpsideDown() {
                return turnUpsideDown;
            }

            public void setTurnUpsideDown(Boolean turnUpsideDown) {
                this.turnUpsideDown = turnUpsideDown;
            }
        }

        public static class Species {
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

        public static class Trigger {
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
}
