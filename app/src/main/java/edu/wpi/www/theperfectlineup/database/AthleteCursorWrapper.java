package edu.wpi.www.theperfectlineup.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.wpi.www.theperfectlineup.Athlete;
import edu.wpi.www.theperfectlineup.database.AthleteDbSchema.AthleteTable;

/**
 * Created by Gina on 2/19/16.
 */
public class AthleteCursorWrapper extends CursorWrapper{
    public AthleteCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Athlete getAthlete() throws ParseException {
        String Id = getString(getColumnIndex(AthleteTable.Cols.ID));
        String firstName = getString(getColumnIndex(AthleteTable.Cols.FIRSTNAME));
        String lastName = getString(getColumnIndex(AthleteTable.Cols.LASTNAME));
        int position = getInt(getColumnIndex(AthleteTable.Cols.POSITION));
        int age = getInt(getColumnIndex(AthleteTable.Cols.AGE));
        int yearsPlayed = getInt(getColumnIndex(AthleteTable.Cols.YEARSPLAYED));
        int feet = getInt(getColumnIndex(AthleteTable.Cols.FEET));
        int inches = getInt(getColumnIndex(AthleteTable.Cols.INCHES));
        int weight = getInt(getColumnIndex(AthleteTable.Cols.WEIGHT));
        int twokMin = getInt(getColumnIndex(AthleteTable.Cols.TWOKMIN));
        int twokSec = getInt(getColumnIndex(AthleteTable.Cols.TWOKSEC));

        Athlete athlete = new Athlete(firstName, lastName, position, age, yearsPlayed,
                feet, inches, weight, twokMin, twokSec);

        return athlete;

    }

}
