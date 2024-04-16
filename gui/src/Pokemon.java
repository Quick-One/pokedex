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
    public String evolvesFrom;
    public String[][] evolvesTo;

    public Pokemon(int id) {
        this.id = id;
    }

    public Pokemon(int id, String name, String generation, int height, int weight, int baseExperience,
                   String color, String habitat, String shape, String[] types, Integer captureRate,
                   Integer isLegendary, Integer isMythical, Move[] moves, String evolvesFrom, String[][] evolvesTo) {
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
        this.evolvesFrom = evolvesFrom;
        this.evolvesTo = evolvesTo;
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

    public String getEvolutions() {
        if (evolvesTo == null || evolvesTo.length == 0) {
            return "None";
        }
        // make a string of the form this.name -> evolvesTo[0][0] -> evolvesTo[0][1]\n this.name -> evolvesTo[1][0] -> evolvesTo[1][n]\n ...
        StringBuilder sb = new StringBuilder();
        for (String[] evolution : evolvesTo) {
            sb.append(name).append(" -> ").append(evolution[0]);
            for (int i = 1; i < evolution.length; i++) {
                sb.append(" -> ").append(evolution[i]);
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
