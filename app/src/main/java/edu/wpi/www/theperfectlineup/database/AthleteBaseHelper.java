package edu.wpi.www.theperfectlineup.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.wpi.www.theperfectlineup.database.AthleteDbSchema.AthleteTable;

/**
 * Created by Gina on 2/19/16.
 */
public class AthleteBaseHelper extends SQLiteOpenHelper{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "athleteBase.db";

    public AthleteBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + AthleteTable.NAME + "(" +
            "_id integer primary key autoincrement," +
            AthleteTable.Cols.ID + ", " +
            AthleteTable.Cols.FIRSTNAME + ", " +
            AthleteTable.Cols.LASTNAME + ", " +
            AthleteTable.Cols.POSITION + ", " +
            AthleteTable.Cols.AGE + ", " +
            AthleteTable.Cols.YEARSPLAYED + "," +
            AthleteTable.Cols.FEET + ", " +
            AthleteTable.Cols.INCHES + ", " +
            AthleteTable.Cols.WEIGHT + ", " +
            AthleteTable.Cols.TWOK + ")"
            );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
