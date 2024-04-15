public class RosterQuery {
    String name;
    Integer id;

    public RosterQuery(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }
}
