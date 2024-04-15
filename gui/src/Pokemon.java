public class Pokemon {
    public int id;
    public String name;
    public String generation;
    public Integer height;
    public Integer weight;
    public Integer baseExperience;
    public String color;
    public String habitat;
    public String shape;
    public String[] types;
    public Integer captureRate;
    public Integer isLegendary;
    public Integer isMythical;
    public Move[] moves;

    public Pokemon(int id, String name, String generation, int height, int weight, int baseExperience,
                   String color, String habitat, String shape, String[] types, Integer captureRate,
                   Integer isLegendary, Integer isMythical, Move[] moves) {
        this.id = id;
        this.name = name;
        this.generation = generation;
        this.height = height;
        this.weight = weight;
        this.baseExperience = baseExperience;
        this.color = color;
        this.habitat = habitat;
        this.shape = shape;
        this.types = types;
        this.captureRate = captureRate;
        this.isLegendary = isLegendary;
        this.isMythical = isMythical;
        this.moves = moves;
    }

    @Override
    public String toString() {
        return name + " (" + id + ")";
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGeneration() {
        if (generation == null) {
            return "N/A";
        }
        return generation;
    }

    public Integer getHeight() {
        if (height == null){
            return null;
        }
        return height;
    }

    public Integer getWeight() {
        if (weight == null){
            return null;
        }
        return weight;
    }

    public Integer getBaseExperience() {
        if (baseExperience == null){
            return null;
        }
        return baseExperience;
    }

    public String getColor() {
        if (color == null){
            return "N/A";
        }
        return color;
    }

    public String getHabitat() {
        if (habitat == null){
            return "N/A";
        }
        return habitat;
    }

    public String getShape() {
        if (shape == null){
            return "N/A";
        }
        return shape;
    }

    public String[] getTypes() {
        if (types == null){
            return new String[0];
        }
        return types;
    }

    public Integer getCaptureRate() {
        if (captureRate == null){
            return null;
        }
        return captureRate;
    }

    public Integer getIsLegendary() {
        if (isLegendary == null){
            return null;
        }
        return isLegendary;
    }

    public Integer getIsMythical() {
        if (isMythical == null){
            return null;
        }
        return isMythical;
    }
}
