package prototype;

public class Passenger {

    private String name;
    private int age;
    private String seat;

    public Passenger(String name, int age, String seat) {
        this.name = name;
        this.age = age;
        this.seat = seat;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getSeat() {
        return seat;
    }
}
