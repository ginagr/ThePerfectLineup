package edu.wpi.www.theperfectlineup.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import edu.wpi.www.theperfectlineup.Athlete;
import edu.wpi.www.theperfectlineup.database.AthleteDbSchema.AthleteTable;

/**
 * Created by Gina on 2/19/16.
 */
public class AthleteCursorWrapper extends CursorWrapper{
    public AthleteCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Athlete getAthlete(){
        String Id = getString(getColumnIndex(AthleteTable.Cols.ID));
        String firstName = getString(getColumnIndex(AthleteTable.Cols.FIRSTNAME));
        String lastName = getString(getColumnIndex(AthleteTable.Cols.LASTNAME));
        int position = getInt(getColumnIndex(AthleteTable.Cols.POSITION));
        int age = getInt(getColumnIndex(AthleteTable.Cols.AGE));
        int yearsPlayed = getInt(getColumnIndex(AthleteTable.Cols.YEARSPLAYED));

        Athlete athlete = new Athlete(firstName, lastName, position, age, yearsPlayed);

        return athlete;

    }

}
