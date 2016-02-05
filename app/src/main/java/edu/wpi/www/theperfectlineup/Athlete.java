package edu.wpi.www.theperfectlineup;

/**
 * Created by Tim on 2/2/2016.
 */
public class Athlete {
    private String name;
    private int age;

    public Athlete(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge()
    {
        return age;
    }
}
