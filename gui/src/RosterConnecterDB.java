import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

public class RosterConnecterDB {
    private static final String DB_USER = System.getenv("DB_USER");
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");
    private static final String DB_HOST = System.getenv("DB_HOST");
    private static final String DB_PORT = System.getenv("DB_PORT");
    private static final String DB_NAME = System.getenv("DB_NAME");

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
        User currentUser = User.getInstance();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("SELECT * FROM roster_user WHERE user_id = ?");
//            ps.setInt(1, currentUser.);
            ResultSet rs = ps.executeQuery();
            CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
            crs.populate(rs);
            RosterQuery[] rosters = new RosterQuery[crs.size()];
            while (crs.next()) {
                rosters[crs.getRow() - 1] = new RosterQuery(crs.getString("name"), crs.getInt("id"));
            }
            return rosters;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Roster getRosterById(int id) {
        // TODO - implement getting a roster by id
        return null;
    }

    public Boolean addRoster(String name) {
        // TODO - implement adding a roster for the user
        return null;
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
