import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

public class RosterConnecterDB {
    private static final String DB_USER = System.getenv("DB_USER");
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");
    private static final String DB_HOST = System.getenv("DB_HOST");
    private static final String DB_PORT = System.getenv("DB_PORT");
    private static final String DB_NAME = System.getenv("DB_NAME");

    public static boolean checkConnection(){
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
        // TODO - implement gettings all rosters for the user
        return null;
    }

    public Roster getRosterById(int id) {
        // TODO - implement getting a roster by id
        return null;
    }

    public void addRoster(String name) {
        // TODO - implement adding a roster for the user
    }

    public Boolean addPokemonToRoster(RosterQuery rq, Pokemon p, Move[] moves) {
        // TODO - implement adding a pokemon to a roster
        return null;
    }

    public Boolean updateRosterName(RosterQuery rq, String newName) {
        // TODO - implement updating a roster name
        return null;
    }

    public Boolean deleteRoster(RosterQuery rq) {
        // TODO - implement deleting a roster
        return null;
    }
}
