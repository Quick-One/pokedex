public class PokemonQuery {
    String name;
    Integer id;

    public PokemonQuery(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }

}
