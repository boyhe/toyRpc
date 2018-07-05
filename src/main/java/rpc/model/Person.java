package rpc.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Person implements Serializable {
    private static final long serialVersionUID = 22L;
    private int id;
    private String name;
    private String city;

    public static Map<Integer, Person> info = new HashMap<>();

    static {
        info.put(0, new Person(0, "Long","beijing"));
        info.put(1, new Person(1, "Ao","nanjing"));
        info.put(5, new Person(5, "Tian","tianjin"));
    }

    public Person(int id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }

    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
