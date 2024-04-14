import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Test connection to database
public class BasicConnection {
    public static void main(String[] args) {
        // getting the environment variables
        String DB_USER = System.getenv("DB_USER");
        String DB_PASSWORD = System.getenv("DB_PASSWORD");
        String DB_HOST = System.getenv("DB_HOST");
        String DB_PORT = System.getenv("DB_PORT");
        String DB_NAME = System.getenv("DB_NAME");

        // test the connection to mysql server
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
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
