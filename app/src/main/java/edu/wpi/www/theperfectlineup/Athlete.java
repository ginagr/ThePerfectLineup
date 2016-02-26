package edu.wpi.www.theperfectlineup;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Tim on 2/2/2016.
 */
public class Athlete implements Parcelable{

    private   String firstName;
    private   String lastName;
    private   int position; //1 = Cox, 2 = Port, 3 = Starboard
    private   int age;
    private   int yearsPlayed;
    private   String  id; // last name + first name

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

    protected Athlete(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        position = in.readInt();
        age = in.readInt();
        yearsPlayed = in.readInt();
        id = in.readString();
    }

    public static final Creator<Athlete> CREATOR = new Creator<Athlete>() {
        @Override
        public Athlete createFromParcel(Parcel in) {
            return new Athlete(in);
        }

        @Override
        public Athlete[] newArray(int size) {
            return new Athlete[size];
        }
    };

    public  String getFirstName() { return firstName; }

    public  String getLastName() { return lastName; }

    public  int getPosition() { return position; }

    public  int getAge() { return age; }

    public  int getYearsPlayed()  { return yearsPlayed; }

    public  String getID() { return id; }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeInt(position);
        dest.writeInt(age);
        dest.writeInt(yearsPlayed);
        dest.writeString(id);
    }
}
