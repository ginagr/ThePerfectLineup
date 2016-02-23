package edu.wpi.www.theperfectlineup;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import edu.wpi.www.theperfectlineup.database.AthleteBaseHelper;
import edu.wpi.www.theperfectlineup.database.AthleteCursorWrapper;
import edu.wpi.www.theperfectlineup.database.AthleteDbSchema;
import edu.wpi.www.theperfectlineup.database.AthleteDbSchema.AthleteTable;

/**
 * Created by Gina on 2/19/16.
 */
public class AthleteRegistration extends AppCompatActivity {
    String TAG = AthleteRegistration.class.getSimpleName();

    private Context mContext;
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

    public AthleteRegistration(){

    }

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

    private AthleteRegistration(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new AthleteBaseHelper(mContext).getWritableDatabase();
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
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

    public void addAthlete(Athlete athlete)
    {
        ContentValues values = getContentValues(athlete);
        Log.d(TAG, "Position: " + newAthlete.getPosition());
        mDatabase.insert(AthleteTable.NAME, null, values);
    }

    public List<Athlete> getAthlete() {
        List<Athlete> athletes = new ArrayList<>();

        AthleteCursorWrapper cursor = queryAthletes(null, null);

        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                athletes.add(cursor.getAthlete());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return athletes;
    }

    public Athlete getAthlete(String id){
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

    public void updateAthlete(Athlete athlete){
        String athleteID = athlete.getID();
        ContentValues values = getContentValues(athlete);

        mDatabase.update(AthleteTable.NAME, values, AthleteTable.Cols.ID +
                " = ? ", new String[]{athleteID});
    }

    private static ContentValues getContentValues(Athlete athlete){
        ContentValues values = new ContentValues();
        values.put(AthleteTable.Cols.ID, athlete.getLastName() + athlete.getFirstName());
        values.put(AthleteTable.Cols.FIRSTNAME, athlete.getFirstName());
        values.put(AthleteTable.Cols.LASTNAME, athlete.getLastName());
        values.put(AthleteTable.Cols.POSITION, athlete.getPosition());
        values.put(AthleteTable.Cols.AGE, athlete.getAge());
        values.put(AthleteTable.Cols.YEARSPLAYED, athlete.getYearsPlayed());

        return values;
    }

    private AthleteCursorWrapper queryAthletes(String whereClause, String[]whereArgs){
        Cursor cursor = mDatabase.query(AthleteTable.NAME, null, whereClause, whereArgs, null, null, null);
        return new AthleteCursorWrapper(cursor);
    }

    public void SubmitAthlete(View view){
        firstNameEdit = (EditText) findViewById(R.id.FirstNameText);
        lastNameEdit = (EditText) findViewById(R.id.LastNameText);
        ageEdit = (EditText) findViewById(R.id.AgeText);
        yearsPlayedEdit = (EditText) findViewById(R.id.YearsPlayedText);
        radioGroup = (RadioGroup) findViewById(R.id.RadioGroup);


        boolean check = checkIfEmpty();

        if(!check) {
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

    public boolean checkIfEmpty(){

        if(firstNameEdit.getText().toString().trim().equals("")) { Toast.makeText(getApplicationContext(), "First name is empty", Toast.LENGTH_SHORT).show(); return true;}
        else { sFirstName = firstNameEdit.getText().toString(); }

        if(lastNameEdit.getText().toString().trim().equals("")) { Toast.makeText(getApplicationContext(), "Last name is empty", Toast.LENGTH_SHORT).show(); return true;}
        else { sLastName = lastNameEdit.getText().toString(); }

        if (ageEdit.getText().toString().trim().equals("")) { Toast.makeText(getApplicationContext(), "Age is empty", Toast.LENGTH_SHORT).show(); return true;}
        else {
            try{
                sAge = Integer.parseInt(ageEdit.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "Please enter a valid number", Toast.LENGTH_SHORT).show();
            }
        }

        if(yearsPlayedEdit.getText().toString().trim().equals("")) { Toast.makeText(getApplicationContext(), "Years played is empty", Toast.LENGTH_SHORT).show(); return true;}
        else {
            try{
                sYearsPlayed = Integer.parseInt(yearsPlayedEdit.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "Please enter a valid number", Toast.LENGTH_SHORT).show();
            }
        }
        if(sPosition == 0) { Toast.makeText(getApplicationContext(), "Position is empty", Toast.LENGTH_SHORT).show(); return true;}

        return false;
    }

    public void backToDrag(View view){
        Intent i = new Intent(AthleteRegistration.this, DropActivity.class);
        startActivity(i);
    }
}
