import java.sql.*;

// Test connection to database
public class DatabaseConnector {
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
    private static ResultSet executeUserDBCommand(String sql, Object... args) {
        // THE RETURN TYPE should be a result set
        // the input type should be whatever there was in the video
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://%s:%s/%s".formatted(DB_HOST, DB_PORT, DB_NAME),
                    DB_USER,
                    DB_PASSWORD
            );

            // create a statement
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }

            // execute the command
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // close the connection
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return resultSet;
    }

    public static User checkLogin(String username, String password) {
        // TODO - implement a check login method
        // check is username already exists or not
        // if it does create a user object and return it
        // user object should have a username and a user id
//        user = new User(username, 1);

        User user = null;
        try (ResultSet resultSet = executeUserDBCommand(
                "SELECT * FROM auth JOIN user ON auth.user_id = user.user_id WHERE username = ? AND password = ?;",
                username, password)) {
            if (resultSet.next()) {
                user = new User(resultSet.getInt("id"),username, resultSet.getString("first_name"), resultSet.getString("last_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    private static void addNewUser(String username, String password, String first_name, String last_name) {
        // TODO - implement an add new user method
        // add a new user to the database

        int user_id = -1;
        try(ResultSet resultSet = executeUserDBCommand(
                "INSERT INTO user (username, first_name, last_name) VALUES (?);",
                username, first_name, last_name)) {
            if (resultSet.next()) {
                user_id = resultSet.getInt(1);
                System.out.println("User added successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (user_id == -1) {
            return;
        }
        try(ResultSet resultSet = executeUserDBCommand(
                "INSERT INTO auth (user_id, password) VALUES (?, ?);",
                user_id, password)) {
            if (resultSet.next()) {
                System.out.println("User added successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkSignUp(String username, String password, String first_name, String last_name) {
        // TODO - implement a check sign up method
        // returns false if the username already exists
        // returns true if the username does not exist

        try(ResultSet resultSet = executeUserDBCommand(
                "SELECT * FROM user WHERE username = ?;",
                username)) {
            if (resultSet.next()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        addNewUser(username, password, first_name, last_name);
        return true;
    }

    public static void main(String[] args) {
        checkConnection();
    }
}
