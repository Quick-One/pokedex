public class PokemonConnectorDB {
    public String[] getAllTypes() {
        // TODO - implement a function to get all types
        return new String[]{"Normal", "Fire", "Water", "Electric", "Grass", "Ice", "Fighting", "Poison", "Ground", "Flying", "Psychic", "Bug", "Rock", "Ghost", "Dragon", "Dark", "Steel", "Fairy"};
    }

    public String[] getAllGenerations() {
        // TODO - implement a function to get all generations
        return new String[]{"generation-i", "generation-ii", "generation-iii", "generation-iv", "generation-v", "generation-vi", "generation-vii", "generation-viii"};

    }

    public PokemonQuery[] searchPokemon(String name, String type, String generation) {
        PokemonQuery item1 = new PokemonQuery("pika", 1);
        PokemonQuery item2 = new PokemonQuery("char", 2);
        return new PokemonQuery[]{item1, item2};
    }

    public Pokemon getPokemonById(int id) {
        // TODO - implement a function to get a pokemon by id
//        return new Pokemon("pika", 1, "Electric", "generation-i");
        return null;
    }
}
