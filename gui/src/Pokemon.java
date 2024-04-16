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

    public String getIsLegendary() {
        if (isLegendary == 1) {
            return "Yes";
        } else if (isLegendary == 0) {
            return "No";
        }
        return "N/A";
    }

    public String getIsMythical() {
        if (isMythical == 1) {
            return "Yes";
        } else if (isMythical == 0) {
            return "No";
        }
        return "N/A";
    }
}
