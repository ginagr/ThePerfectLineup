package edu.wpi.www.theperfectlineup;

/**
 * Created by Tim on 2/2/2016.
 */
public class Athlete {

    private static  String firstName;
    private static  String lastName;
    private static  int position; //1 = Cox, 2 = Port, 3 = Starboard
    private static  int age;
    private static  int yearsPlayed;
    private static  String  id; // last name + first name

    public Athlete(String firstName, String lastName, int position, int age, int yearsPlayed) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.age = age;
        this.yearsPlayed = yearsPlayed;
        id = lastName + firstName;
    }

    public Athlete(String firstName, int age){
        this.firstName = firstName;
        this.age = age;
    }

    public static String getFirstName() { return firstName; }

    public static String getLastName() { return lastName; }

    public static int getPosition() { return position; }

    public static int getAge() { return age; }

    public static int getYearsPlayed()  { return yearsPlayed; }

    public static String getID() { return id; }



}
