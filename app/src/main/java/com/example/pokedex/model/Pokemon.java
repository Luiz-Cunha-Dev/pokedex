package com.example.pokedex.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Pokemon {
    private int id;
    private String name;
    private int height;
    private int weight;
    private List<Type> types;
    private List<Ability> abilities;
    private List<Stat> stats;
    private Sprites sprites;

    @SerializedName("base_experience")
    private int baseExperience;
    private List<Move> moves;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    public List<Stat> getStats() {
        return stats;
    }

    public void setStats(List<Stat> stats) {
        this.stats = stats;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
    }

    public int getBaseExperience() {
        return baseExperience;
    }

    public void setBaseExperience(int baseExperience) {
        this.baseExperience = baseExperience;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public static class Type {
        @SerializedName("type")
        private TypeInfo typeInfo;

        public TypeInfo getTypeInfo() {
            return typeInfo;
        }

        public void setTypeInfo(TypeInfo typeInfo) {
            this.typeInfo = typeInfo;
        }

        public static class TypeInfo {
            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }

    public static class Ability {
        @SerializedName("ability")
        private AbilityInfo abilityInfo;

        public AbilityInfo getAbilityInfo() {
            return abilityInfo;
        }

        public void setAbilityInfo(AbilityInfo abilityInfo) {
            this.abilityInfo = abilityInfo;
        }

        public static class AbilityInfo {
            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }

    public static class Stat {
        @SerializedName("base_stat")
        private int baseStat;

        @SerializedName("stat")
        private StatInfo statInfo;

        public int getBaseStat() {
            return baseStat;
        }

        public void setBaseStat(int baseStat) {
            this.baseStat = baseStat;
        }

        public StatInfo getStatInfo() {
            return statInfo;
        }

        public void setStatInfo(StatInfo statInfo) {
            this.statInfo = statInfo;
        }

        public static class StatInfo {
            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }

    public static class Sprites {
        @SerializedName("front_default")
        private String frontDefault;

        @SerializedName("front_shiny")
        private String frontShiny;

        private Other other;

        public String getFrontDefault() {
            return frontDefault;
        }

        public void setFrontDefault(String frontDefault) {
            this.frontDefault = frontDefault;
        }

        public String getFrontShiny() {
            return frontShiny;
        }

        public void setFrontShiny(String frontShiny) {
            this.frontShiny = frontShiny;
        }

        public Other getOther() {
            return other;
        }

        public void setOther(Other other) {
            this.other = other;
        }

        public static class Other {
            private Home home;

            @SerializedName("official-artwork")
            private OfficialArtwork officialArtwork;

            public Home getHome() {
                return home;
            }

            public void setHome(Home home) {
                this.home = home;
            }

            public OfficialArtwork getOfficialArtwork() {
                return officialArtwork;
            }

            public void setOfficialArtwork(OfficialArtwork officialArtwork) {
                this.officialArtwork = officialArtwork;
            }

            public static class Home {
                @SerializedName("front_default")
                private String frontDefault;

                @SerializedName("front_shiny")
                private String frontShiny;

                public String getFrontDefault() {
                    return frontDefault;
                }

                public void setFrontDefault(String frontDefault) {
                    this.frontDefault = frontDefault;
                }

                public String getFrontShiny() {
                    return frontShiny;
                }

                public void setFrontShiny(String frontShiny) {
                    this.frontShiny = frontShiny;
                }
            }

            public static class OfficialArtwork {
                @SerializedName("front_default")
                private String frontDefault;

                @SerializedName("front_shiny")
                private String frontShiny;

                public String getFrontDefault() {
                    return frontDefault;
                }

                public void setFrontDefault(String frontDefault) {
                    this.frontDefault = frontDefault;
                }

                public String getFrontShiny() {
                    return frontShiny;
                }

                public void setFrontShiny(String frontShiny) {
                    this.frontShiny = frontShiny;
                }
            }
        }
    }

    public static class Move {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}