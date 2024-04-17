//public class User {
//    public String username;
//    public int userId;
//    public String first_name;
//    public String last_name;
//
//    public User(int userId, String username, String first_name, String last_name) {
//        this.userId = userId;
//        this.username = username;
//        this.first_name = first_name;
//        this.last_name = last_name;
//    }
//}

// SINGLETON
public class User {
    private static User instance;
    public String username;
    public int userId;
    public String first_name;
    public String last_name;
    public boolean isLoggedIn = false;

    private User() {
    }

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public String toString(){
        return username + "(" + first_name + " " + last_name + ")";
    }
}