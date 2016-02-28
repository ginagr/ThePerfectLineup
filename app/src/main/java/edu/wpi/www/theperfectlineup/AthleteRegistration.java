package edu.wpi.www.theperfectlineup;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;

import edu.wpi.www.theperfectlineup.database.AthleteBaseHelper;
import edu.wpi.www.theperfectlineup.database.AthleteCursorWrapper;
import edu.wpi.www.theperfectlineup.database.AthleteDbSchema.AthleteTable;

/**
 * Created by Gina on 2/19/16.
 */
public class AthleteRegistration extends AppCompatActivity implements Parcelable {
    String TAG = AthleteRegistration.class.getSimpleName();

    private Context mContext = this;
    private SQLiteDatabase mDatabase;

    EditText firstNameEdit;
    EditText lastNameEdit;
    EditText ageEdit;
    EditText yearsPlayedEdit;
    RadioGroup radioGroup;

    public static String id; //Last Name + First Name
    public static String sFirstName;
    public static String sLastName;
    public static int sPosition; //1 = Cox, 2 = Port, 3 = Starboard
    public static int sAge;
    public static int sYearsPlayed;

    public static Athlete newAthlete;
    private static final String EXTRA_ARRAY_LIST = "the.perfect.lineup.array.list";

    public AthleteRegistration() {
    }

    protected AthleteRegistration(Parcel in) {
        TAG = in.readString();
    }

    public static final Creator<AthleteRegistration> CREATOR = new Creator<AthleteRegistration>() {
        @Override
        public AthleteRegistration createFromParcel(Parcel in) {
            return new AthleteRegistration(in);
        }

        @Override
        public AthleteRegistration[] newArray(int size) {
            return new AthleteRegistration[size];
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.athlete_registration);
        Context context = this;
        mContext = context.getApplicationContext();
        mDatabase = new AthleteBaseHelper(mContext).getWritableDatabase();

    }

    //TODO: put in athlete fragment
//    @Override
//    public void onPause() {
//        super.onPause();
//        AthleteRegistration.get(getActivity()).updateAthlete(newAthlete);
//    }

    private AthleteRegistration(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new AthleteBaseHelper(mContext).getWritableDatabase();
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.StarboardRadio:
                if (checked)
                    sPosition = 3;
                break;
            case R.id.PortRadio:
                if (checked)
                    sPosition = 2;
                break;
            case R.id.CoxwainRadio:
                if (checked)
                    sPosition = 1;
                break;
        }

    }

    public void addAthlete(Athlete athlete) {
        ContentValues values = getContentValues(athlete);
        mDatabase.insert(AthleteTable.NAME, null, values);
    }

    public ArrayList<Athlete> getAthletes(){
        Context context = this;
        mContext = context.getApplicationContext();
        mDatabase = new AthleteBaseHelper(mContext).getWritableDatabase();

        ArrayList<Athlete> athletes = new ArrayList<>();

        AthleteCursorWrapper cursor = queryAthletes(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                athletes.add(cursor.getAthlete());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return athletes;
    }

    private Context getContext() {
        return this;
    }

    public Athlete getAthlete(String id) {
        AthleteCursorWrapper cursor = queryAthletes(AthleteTable.Cols.ID +
                " = ?", new String[]{id});

        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getAthlete();
        } finally {
            cursor.close();
        }
    }

    public void updateAthlete(Athlete athlete) {
        String athleteID = athlete.getID();
        ContentValues values = getContentValues(athlete);

        mDatabase.update(AthleteTable.NAME, values, AthleteTable.Cols.ID +
                " = ? ", new String[]{athleteID});
    }

    private static ContentValues getContentValues(Athlete athlete) {
        ContentValues values = new ContentValues();
        values.put(AthleteTable.Cols.ID, athlete.getLastName() + athlete.getFirstName());
        values.put(AthleteTable.Cols.FIRSTNAME, athlete.getFirstName());
        values.put(AthleteTable.Cols.LASTNAME, athlete.getLastName());
        values.put(AthleteTable.Cols.POSITION, athlete.getPosition());
        values.put(AthleteTable.Cols.AGE, athlete.getAge());
        values.put(AthleteTable.Cols.YEARSPLAYED, athlete.getYearsPlayed());
        values.put(AthleteTable.Cols.FEET, athlete.getFeet());
        values.put(AthleteTable.Cols.INCHES, athlete.getInches());
        values.put(AthleteTable.Cols.WEIGHT, athlete.getWeight());
        values.put(AthleteTable.Cols.TWOK, athlete.getTwok());

        return values;
    }

    private AthleteCursorWrapper queryAthletes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(AthleteTable.NAME, null, whereClause, whereArgs, null, null, null);
        return new AthleteCursorWrapper(cursor);
    }

    public void SubmitAthlete(View view) {
        firstNameEdit = (EditText) findViewById(R.id.FirstNameText);
        lastNameEdit = (EditText) findViewById(R.id.LastNameText);
        ageEdit = (EditText) findViewById(R.id.AgeText);
        yearsPlayedEdit = (EditText) findViewById(R.id.YearsPlayedText);
        radioGroup = (RadioGroup) findViewById(R.id.RadioGroup);

        boolean check = checkIfEmpty();

        if (!check) {
            id = sLastName + sFirstName;

            newAthlete = new Athlete(sFirstName, sLastName, sPosition, sAge, sYearsPlayed);

            addAthlete(newAthlete);

            firstNameEdit.setText("");
            lastNameEdit.setText("");
            ageEdit.setText("");
            yearsPlayedEdit.setText("");
            radioGroup.clearCheck();
        }
    }

    public boolean checkIfEmpty() {

        if (firstNameEdit.getText().toString().trim().equals("")) {
            Toast.makeText(getApplicationContext(), "First name is empty", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            sFirstName = firstNameEdit.getText().toString();
        }

        if (lastNameEdit.getText().toString().trim().equals("")) {
            Toast.makeText(getApplicationContext(), "Last name is empty", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            sLastName = lastNameEdit.getText().toString();
        }

        if (ageEdit.getText().toString().trim().equals("")) {
            Toast.makeText(getApplicationContext(), "Age is empty", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            try {
                sAge = Integer.parseInt(ageEdit.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "Please enter a valid number", Toast.LENGTH_SHORT).show();
            }
        }

        if (yearsPlayedEdit.getText().toString().trim().equals("")) {
            Toast.makeText(getApplicationContext(), "Years played is empty", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            try {
                sYearsPlayed = Integer.parseInt(yearsPlayedEdit.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "Please enter a valid number", Toast.LENGTH_SHORT).show();
            }
        }
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getApplicationContext(), "Position is empty", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public void backToDrag(View view) {

        Intent i = new Intent(AthleteRegistration.this, DropActivity.class);
        ArrayList<Athlete> ath = getAthletes();
        Log.d(TAG, "Num of Athletes: " + ath.size());
        i.putParcelableArrayListExtra(EXTRA_ARRAY_LIST, ath);
        startActivity(i);
    }

    public void deleteAthlete(View view){


    }

    @Override
    public int describeContents() { return 0; }
    @Override
    public void writeToParcel(Parcel dest, int flags) { dest.writeString(TAG);}
}
