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
    private boolean checkConnection() {
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
        } finally {
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        } finally {
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
            String query = "SELECT DISTINCT p.id as id, p.identifier as name FROM pokemon p " +
                    "JOIN pokemon_types pt ON p.id = pt.pokemon_id " +
                    "JOIN types t ON pt.type_id = t.id " +
                    "JOIN pokemon_species ps ON p.species_id = ps.id " +
                    "JOIN generations g ON ps.generation_id = g.id " +
                    "WHERE p.identifier LIKE ? AND t.identifier LIKE ? AND g.identifier LIKE ? " +
                    "ORDER BY name";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name + "%");
            preparedStatement.setString(2, type);
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
        } finally {
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Move[] getMovesByPokemonId(int id) {
        // TODO - implement a function to get moves by pokemon id
        PreparedStatement preparedStatement = null;
        try {
            String query = "SELECT DISTINCT m.id as id, m.identifier as name, m.power, m.pp, m.accuracy, m.priority, m.damage_class_id, t.identifier as type, mdc.identifier as damage_class " +
                    "FROM moves m " +
                    "JOIN pokemon_moves pm ON m.id = pm.move_id " +
                    "JOIN types t ON m.type_id = t.id " +
                    "JOIN move_damage_classes mdc ON m.damage_class_id = mdc.id " +
                    "WHERE pm.pokemon_id = ? " +
                    "ORDER BY name";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            List<Move> moves = new ArrayList<>();
            while (rs.next()) {
                moves.add(new Move(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getInt("power"),
                        rs.getInt("pp"),
                        rs.getInt("accuracy"),
                        rs.getInt("priority"),
                        rs.getString("damage_class")
                ));
            }
            return moves.toArray(new Move[0]);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String[][] getEvolutions(int poke_id) {
        PreparedStatement preparedStatement = null;
        try {
            String query = "SELECT pokemon.id as id, pokemon.identifier as name, ps2.identifier as evolves_to1, ps3.identifier as evolves_to2 " +
                    "FROM pokemon " +
                    "JOIN pokemon_species ps ON pokemon.species_id = ps.id " +
                    "LEFT JOIN pokemon_species ps2 ON ps2.evolves_from_species_id = ps.id " +
                    "LEFT JOIN pokemon_species ps3 ON ps2.id = ps3.evolves_from_species_id " +
                    "WHERE pokemon.id = ?";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, poke_id);

            ResultSet rs = preparedStatement.executeQuery();
            List<String[]> evolutions = new ArrayList<>();

            while (rs.next()) {
                List<String> evolution = new ArrayList<>();
                if (rs.getString("evolves_to1") != null) {
                    evolution.add(rs.getString("evolves_to1"));
                }

                if (rs.getString("evolves_to2") != null) {
                    evolution.add(rs.getString("evolves_to2"));
                }
                if (!evolution.isEmpty()) {
                    evolutions.add(evolution.toArray(new String[0]));
                }
            }

            return evolutions.toArray(new String[0][0]);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Pokemon getPokemonById(int id) {
        // TODO - implement a function to get a pokemon by id
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        try {
            String query = "SELECT p.id as id, p.identifier as name,  p.height, p.weight, p.base_experience, " +
                    "ps.capture_rate, ps.is_legendary, ps.is_mythical, ps2.identifier as evolves_from, " +
                    "g.identifier as generation," +
                    "c.identifier as color, h.identifier as habitat, s.identifier as shape " +
                    "FROM pokemon p " +
                    "LEFT JOIN pokemon_species ps ON p.species_id = ps.id " +
                    "LEFT JOIN pokemon_species ps2 ON ps2.id = ps.evolves_from_species_id " +
                    "LEFT JOIN generations g ON ps.generation_id = g.id " +
                    "LEFT JOIN pokemon_colors c ON ps.color_id = c.id " +
                    "LEFT JOIN pokemon_habitats h ON ps.habitat_id = h.id " +
                    "LEFT JOIN pokemon_shapes s ON ps.shape_id = s.id " +
                    "LEFT JOIN pokemon_types pt ON p.id = pt.pokemon_id " +
                    "WHERE p.id = ?";

            preparedStatement1 = connection.prepareStatement(query);
            preparedStatement1.setInt(1, id);
            ResultSet rs1 = preparedStatement1.executeQuery();

            preparedStatement2 = connection.prepareStatement(
                    "SELECT DISTINCT t.identifier as type FROM types t JOIN pokemon_types pt ON t.id = pt.type_id WHERE pt.pokemon_id = ?");
            preparedStatement2.setInt(1, id);
            ResultSet rs2 = preparedStatement2.executeQuery();

            List<String> types = new ArrayList<>();
            while (rs2.next()) {
                types.add(rs2.getString("type"));
            }

            Move[] moves = getMovesByPokemonId(id);
            String[][] evolvesTo = getEvolutions(id);
            System.out.println(evolvesTo);

            if (rs1.next()) {
                return new Pokemon(
                        rs1.getInt("id"),
                        rs1.getString("name"),
                        rs1.getString("generation"),
                        rs1.getInt("height"),
                        rs1.getInt("weight"),
                        rs1.getInt("base_experience"),
                        rs1.getString("color"),
                        rs1.getString("habitat"),
                        rs1.getString("shape"),
                        types.toArray(new String[0]),
                        rs1.getInt("capture_rate"),
                        rs1.getInt("is_legendary"),
                        rs1.getInt("is_mythical"),
                        moves,
                        rs1.getString("evolves_from"),
                        evolvesTo
                );
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                preparedStatement1.close();
                preparedStatement2.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    public static void main(String[] args) {
        PokemonConnectorDB pokemonConnectorDB = new PokemonConnectorDB();

//        String[] types = pokemonConnectorDB.getAllTypes();
//        for (String type : types) {
//            System.out.println(type);
//        }
//
//        String[] generations = pokemonConnectorDB.getAllGenerations();
//        for (String generation : generations) {
//            System.out.println(generation);
//        }
//
//        PokemonQuery[] pokemonQueries = pokemonConnectorDB.searchPokemon("pikachu", "electric", "generation-i");
//        for (PokemonQuery pokemonQuery : pokemonQueries) {
//            System.out.println(pokemonQuery.name + " " + pokemonQuery.id);
//        }
//
//        Move[] moves = pokemonConnectorDB.getMovesByPokemonId(25);
//        for (Move move : moves) {
//            System.out.println(move.name + " " + move.type + " " + move.power + " " + move.pp + " " + move.accuracy + " " + move.priority + " " + move.damageClass);
//        }
//
//        Pokemon pokemon = pokemonConnectorDB.getPokemonById(25);
//        System.out.println(pokemon.name + " " + pokemon.generation + " " + pokemon.height + " " + pokemon.weight + " " + pokemon.baseExperience + " " + pokemon.color + " " + pokemon.habitat + " " + pokemon.shape + " " + pokemon.captureRate + " " + pokemon.isLegendary + " " + pokemon.isMythical);
        // test getEvolutions
        String[][] evolutions = pokemonConnectorDB.getEvolutions(133);
        for (String[] evolution : evolutions) {
            for (String e : evolution) {
                System.out.print(e + " ");
            }
            System.out.println();
        }
    }
}
