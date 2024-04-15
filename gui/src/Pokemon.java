public class Pokemon {
    public int id;
    public String name;
    public String type;
    public String generation;
    public int height;
    public int weight;
    public int baseExperience;
    public String color;
    public String habitat;
    public String shape;
    public String[] types;

    public Pokemon(String name, String type, String generation) {
        this.name = name;
        this.type = type;
        this.generation = generation;
    }
}
