public class User {
    public String username;
    public int userId;
    public String first_name;
    public String last_name;

    public User(int userId, String username, String first_name, String last_name) {
        this.userId = userId;
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
    }
}
