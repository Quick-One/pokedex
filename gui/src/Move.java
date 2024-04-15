public class Move {
    public int id;
    public String name;
    public String type;
    public int power;
    public int pp;
    public int accuracy;
    public int priority;
    public String damageClass;

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
