package edu.wpi.www.theperfectlineup;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by Tim on 2/2/2016.
 */
public class Athlete implements Parcelable{

    private   String firstName;
    private   String lastName;
    private   int position; //1 = Cox, 2 = Port, 3 = Starboard
    private   int age;
    private   int yearsPlayed;
    private   String id; // last name + first name
    private   int feet;
    private   int inches;
    private   int weight;
    private   int twokMin;
    private   double twokSec;


    public Athlete(String firstName, String lastName, int position, int age, int yearsPlayed
    , int feet, int inches, int weight, int twokMin, double twokSec) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.age = age;
        this.yearsPlayed = yearsPlayed;
        this.feet = feet;
        this.inches = inches;
        this.weight = weight;
        this.twokMin = twokMin;
        this.twokSec = twokSec;
    }

    protected Athlete(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        position = in.readInt();
        age = in.readInt();
        yearsPlayed = in.readInt();
        feet = in.readInt();
        inches = in.readInt();
        weight = in.readInt();
        twokMin = in.readInt();
        twokSec = in.readDouble();
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

    public int getFeet() {return feet;}

    public void setFeet(int feet) {this.feet = feet;}

    public int getInches() {return inches;}

    public void setInches(int inches) {this.inches = inches;}

    public int getWeight() {return weight;}

    public void setWeight(int weight) {this.weight = weight;}

    public void setTwokMin(int twokMin) {this.twokMin = twokMin;}

    public void setTwokSec(double twokMin) {this.twokSec = twokSec;}

    public int getTwokMin() { return twokMin; }

    public double getTwokSec() { return twokSec; }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setYearsPlayed(int yearsPlayed) {
        this.yearsPlayed = yearsPlayed;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getPhotoFilename() {
        Log.d("ID: ",getID()+ " name: " + getFirstName());
        String id_here = getID();
        return "IMG_"+ id_here + ".jpg";
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeInt(position);
        dest.writeInt(age);
        dest.writeInt(yearsPlayed);
        dest.writeInt(feet);
        dest.writeInt(inches);
        dest.writeInt(weight);
        dest.writeInt(twokMin);
        dest.writeDouble(twokSec);
        dest.writeString(id);
    }

}
