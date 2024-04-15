import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Test connection to database
public class BasicConnection {
    private static final String DB_USER = System.getenv("DB_USER");
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");
    private static final String DB_HOST = System.getenv("DB_HOST");
    private static final String DB_PORT = System.getenv("DB_PORT");
    private static final String DB_NAME = System.getenv("DB_NAME");

    // returns a result set of the query
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

    // TODO - implement an execute command method
    private static void executeCommand(String command) {
        // THE RETURN TYPE should be a result set
        // the input type should be whatever there was in the video
    }

    public static boolean checkLogin(String username, String password) {
        // TODO - implement a check login method
        return true;
    }

    private static void addNewUser(String username, String password) {
        // TODO - implement an add new user method
    }

    public static boolean checkSignUp(String username, String password) {
        // TODO - implement a check sign up method
        // THIS FUNCTION CALLS THE ADD NEW USER METHOD
        return true;
    }
}
