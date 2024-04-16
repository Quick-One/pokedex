public class Move {
    public Integer id;
    public String name;
    public String type;
    public Integer power;
    public Integer pp;
    public Integer accuracy;
    public Integer priority;
    public String damageClass;

    public Move(int id) {
        this.id = id;
    }

    public Move(int id, String name, String type, int power, int pp, int accuracy, int priority, String damageClass) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.power = power;
        this.pp = pp;
        this.accuracy = accuracy;
        this.priority = priority;
        this.damageClass = damageClass;
    }

    @Override
    public String toString() {
        return name;
    }
}
