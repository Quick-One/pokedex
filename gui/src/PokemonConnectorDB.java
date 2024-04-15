import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PokemonConnectorDB {
    private final String DB_USER = System.getenv("DB_USER");
    private final String DB_PASSWORD = System.getenv("DB_PASSWORD");
    private final String DB_HOST = System.getenv("DB_HOST");
    private final String DB_PORT = System.getenv("DB_PORT");
    private final String DB_NAME = System.getenv("DB_NAME");

    private final Connection connection;

    private Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://%s:%s/%s".formatted(DB_HOST, DB_PORT, DB_NAME),
                    DB_USER,
                    DB_PASSWORD
            );
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            return null;
        }
    }

    // constructor
    public PokemonConnectorDB() {
        if (!checkConnection()) {
            System.exit(1);
        }
        this.connection = getConnection();
    }

    // returns a result set of the query
    private boolean checkConnection(){
        try {
            // create a connection to the database
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://%s:%s/%s".formatted(DB_HOST, DB_PORT, DB_NAME),
                    DB_USER,
                    DB_PASSWORD
            );

            // test the connection
            if (connection != null) {
                System.out.println("Connected to the database!");
                connection.close();
                return true;
            } else {
                System.out.println("Failed to make connection!");
                return false;
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String[] getAllTypes() {
        // TODO - implement a function to get all types
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT DISTINCT identifier FROM types");
            ResultSet rs = preparedStatement.executeQuery();
            List<String> types = new ArrayList<>();
            while (rs.next()) {
                types.add(rs.getString("identifier"));
            }
            return types.toArray(new String[0]);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String[] getAllGenerations() {
        // TODO - implement a function to get all generations
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT DISTINCT identifier FROM generations");
            ResultSet rs = preparedStatement.executeQuery();
            List<String> generations = new ArrayList<>();
            while (rs.next()) {
                generations.add(rs.getString("identifier"));
            }
            return generations.toArray(new String[0]);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public PokemonQuery[] searchPokemon(String name, String type, String generation) {
        // TODO - implement a function to search for a pokemon

        if (name == null) {
            name = "";
        }

        if (type == null) {
            type = "%%";
        }

        if (generation == null) {
            generation = "%%";
        }

        PreparedStatement preparedStatement = null;
        try {
            String query = "SELECT p.id as id, p.identifier as name, t.identifier as type, g.identifier as gen FROM pokemon p " +
                    "JOIN pokemon_types pt ON p.id = pt.pokemon_id " +
                    "JOIN types t ON pt.type_id = t.id " +
                    "JOIN pokemon_species ps ON p.species_id = ps.id " +
                    "JOIN generations g ON ps.generation_id = g.id " +
                    "WHERE p.identifier LIKE ? AND t.identifier LIKE ? AND g.identifier LIKE ?" +
                    "ORDER BY name";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + name + "%");
            preparedStatement.setString(2,  type);
            preparedStatement.setString(3, generation);

            ResultSet rs = preparedStatement.executeQuery();
            List<PokemonQuery> pokemonQueries = new ArrayList<>();
            while (rs.next()) {
                PokemonQuery pokemonQuery = new PokemonQuery(rs.getString("name"), rs.getInt("id"));
                pokemonQueries.add(pokemonQuery);
            }
            return pokemonQueries.toArray(new PokemonQuery[0]);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    public static void main(String[] args) {
        PokemonConnectorDB pokemonConnectorDB = new PokemonConnectorDB();

        String[] types = pokemonConnectorDB.getAllTypes();
        for (String type : types) {
            System.out.println(type);
        }

        String[] generations = pokemonConnectorDB.getAllGenerations();
        for (String generation : generations) {
            System.out.println(generation);
        }

        PokemonQuery[] pokemonQueries = pokemonConnectorDB.searchPokemon("pikachu", "electric", "generation-i");
        for (PokemonQuery pokemonQuery : pokemonQueries) {
            System.out.println(pokemonQuery.name + " " + pokemonQuery.id);
        }
    }
}
