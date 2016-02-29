package edu.wpi.www.theperfectlineup;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private ImageView mPhotoView;

    EditText firstNameEdit;
    EditText lastNameEdit;
    EditText ageEdit;
    EditText yearsPlayedEdit;
    EditText twokMinEdit;
    EditText twokSecEdit;
    EditText weightEdit;
    EditText feetEdit;
    EditText inchesEdit;
    RadioGroup radioGroup;

    public static String id; //Last Name + First Name
    public static String sFirstName;
    public static String sLastName;
    public static int sPosition; //1 = Cox, 2 = Port, 3 = Starboard
    public static int sAge;
    public static int sYearsPlayed;
    public static int sTwokMin;
    public static double sTwokSec;
    public static int sWeight;
    public static int sInches;
    public static int sFeet;

    public static Athlete newAthlete;
    private static final String EXTRA_ARRAY_LIST = "the.perfect.lineup.array.list";
    private File mPhotoFile;
    private static final int REQUEST_PHOTO = 2;

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
        mPhotoView = (ImageView) findViewById(R.id.athlete_photo);
        mPhotoFile = getPhotoFile(newAthlete);


        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Uri uri = Uri.fromFile(mPhotoFile);
        captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

    }

    //TODO: put in athlete fragment
//    @Override
//    public void onPause() {
//        super.onPause();
//        AthleteRegistration.get(getActivity()).updateAthlete(newAthlete);
//    }

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

    public ArrayList<Athlete> getAthletes() throws ParseException {
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

    public Athlete getAthlete(String id) throws ParseException {
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
        values.put(AthleteTable.Cols.TWOKMIN, athlete.getTwokMin());
        values.put(AthleteTable.Cols.TWOKSEC, athlete.getTwokSec());

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
        weightEdit = (EditText) findViewById(R.id.WeightText);
        feetEdit = (EditText) findViewById(R.id.FeetText);
        inchesEdit = (EditText) findViewById(R.id.InchesText);
        twokMinEdit = (EditText) findViewById(R.id.TwoKMinText);
        twokSecEdit = (EditText) findViewById(R.id.TwoKSecText);
        radioGroup = (RadioGroup) findViewById(R.id.RadioGroup);

        boolean check = checkIfEmpty();

        if (!check) {
            id = sLastName + sFirstName;

            newAthlete = new Athlete(sFirstName, sLastName, sPosition, sAge, sYearsPlayed,
                    sFeet, sInches, sWeight, sTwokMin, sTwokSec);

            addAthlete(newAthlete);

            firstNameEdit.setText("");
            lastNameEdit.setText("");
            ageEdit.setText("");
            yearsPlayedEdit.setText("");
            weightEdit.setText("");
            feetEdit.setText("");
            inchesEdit.setText("");
            twokMinEdit.setText("");
            twokSecEdit.setText("");
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

        if (feetEdit.getText().toString().trim().equals("")) {
            Toast.makeText(getApplicationContext(), "Feet is empty", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            try {
                sFeet = Integer.parseInt(feetEdit.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "Please enter a valid number", Toast.LENGTH_SHORT).show();
            }
        }

        if (inchesEdit.getText().toString().trim().equals("")) {
            Toast.makeText(getApplicationContext(), "Inches is empty", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            try {
                sInches = Integer.parseInt(inchesEdit.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "Please enter a valid number", Toast.LENGTH_SHORT).show();
            }
        }

        if (weightEdit.getText().toString().trim().equals("")) {
            Toast.makeText(getApplicationContext(), "Weight is empty", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            try {
                sWeight = Integer.parseInt(weightEdit.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "Please enter a valid number", Toast.LENGTH_SHORT).show();
            }
        }

        if (twokMinEdit.getText().toString().trim().equals("")) {
            Toast.makeText(getApplicationContext(), "2k minutes is empty", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            try {
                sTwokMin = Integer.parseInt(twokMinEdit.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "Please enter a valid number", Toast.LENGTH_SHORT).show();
            }
        }

        if (twokSecEdit.getText().toString().trim().equals("")) {
            Toast.makeText(getApplicationContext(), "2k seconds is empty", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            try {
                sTwokSec = Double.parseDouble(twokSecEdit.getText().toString());
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

    public void backToDrag(View view) throws ParseException {

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



    public File getPhotoFile (Athlete athlete) {
        File externalFilesDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (externalFilesDir == null) {
            return null;
        }
        return new File (externalFilesDir, athlete.getPhotoFilename());
    }


}
