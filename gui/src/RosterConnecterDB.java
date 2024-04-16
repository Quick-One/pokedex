import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RosterConnecterDB {
    private static final String DB_USER = System.getenv("DB_USER");
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");
    private static final String DB_HOST = System.getenv("DB_HOST");
    private static final String DB_PORT = System.getenv("DB_PORT");
    private static final String DB_NAME = System.getenv("DB_NAME");
    PokemonConnectorDB pokemonConnectorDB = new PokemonConnectorDB();

    private Connection connection;

    private Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://%s:%s/%s".formatted(DB_HOST, DB_PORT, DB_NAME),
                        DB_USER,
                        DB_PASSWORD
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public RosterConnecterDB() {
        if (!checkConnection()) {
            System.exit(1);
        }
        this.connection = getConnection();
    }

    public static boolean checkConnection() {
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

    public RosterQuery[] getAllRosters() {
        User currentUser = User.getInstance();
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement("SELECT roster_name as name, roster_id as id FROM roster_user WHERE user_id = ?");
            ps.setInt(1, currentUser.userId);

            ResultSet rs = ps.executeQuery();
            List<RosterQuery> rosters = new ArrayList<>();
            while (rs.next()) {
                rosters.add(new RosterQuery(rs.getString("name"), rs.getInt("id")));
            }
            return rosters.toArray(new RosterQuery[0]);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private List<Integer> getMovesInRosterOfPokemon(int roster_id, int pokemon_id) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("SELECT move_id FROM roster WHERE roster_id = ? AND pokemon_id = ?");
            ps.setInt(1, roster_id);
            ps.setInt(2, pokemon_id);

            ResultSet rs = ps.executeQuery();
            List<Integer> moves = new ArrayList<>();
            while (rs.next()) {
                moves.add(rs.getInt("move_id"));
            }
            return moves;

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public Roster getRosterById(RosterQuery rq) {
        // TODO - implement getting a roster by id
        PreparedStatement ps = null;
        Roster roster = null;
        try {
            ps = connection.prepareStatement("SELECT DISTINCT r.roster_id as rid, r.pokemon_id FROM roster_user ru JOIN roster r ON ru.roster_id = r.roster_id WHERE r.roster_id = ?");
            ps.setInt(1, rq.id);

            ResultSet rs = ps.executeQuery();
            List<Pokemon> pokemon = new ArrayList<>();
            while (rs.next()) {
                pokemon.add(pokemonConnectorDB.getPokemonById(rs.getInt("pokemon_id")));
            }
            System.out.println("ROSTER" + pokemon.size());
            for (Pokemon p : pokemon) {
                List<Integer> validMoveIDs = getMovesInRosterOfPokemon(rq.id, p.id);
                ArrayList<Move> moves = new ArrayList<>();
                for (Move m : p.moves){
                    if (validMoveIDs.contains(m.id)){
                        moves.add(m);
                    }
                }
                p.moves = moves.toArray(new Move[0]);
            }
            roster = new Roster(rq.id, rq.name, pokemon.toArray(new Pokemon[0]));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return roster;
    }

    public Boolean addRoster(String name) {
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement("INSERT INTO roster_user (roster_name, user_id) VALUES (?, ?)");
            ps.setString(1, name);
            ps.setInt(2, User.getInstance().userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public Boolean addPokemonToRoster(RosterQuery rq, Pokemon p, Move[] moves) {
        PreparedStatement ps = null;
        for (Move move : moves) {
            try {
                ps = connection.prepareStatement("INSERT INTO roster (roster_id, pokemon_id, move_id) VALUES (?, ?, ?)");
                ps.setInt(1, rq.id);
                ps.setInt(2, p.id);
                ps.setInt(3, move.id);
                if (ps.executeUpdate() <= 0) {
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            } finally {
                try {
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }

    public Boolean updateRosterName(RosterQuery rq, String newName) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("UPDATE roster_user SET roster_name = ? WHERE roster_id = ?");
            ps.setString(1, newName);
            ps.setInt(2, rq.id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public Boolean deleteRoster(RosterQuery rq) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("DELETE FROM roster_user WHERE roster_id = ?");
            ps.setInt(1, rq.id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static void main(String[] args) {
//        User.getInstance()

        RosterConnecterDB rcd = new RosterConnecterDB();

        rcd.addRoster("roster name");

        RosterQuery[] rosters = rcd.getAllRosters();
        for (RosterQuery roster : rosters) {
            System.out.println(roster);
        }
    }
}
