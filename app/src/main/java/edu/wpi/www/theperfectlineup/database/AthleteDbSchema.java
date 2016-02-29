package edu.wpi.www.theperfectlineup.database;

/**
 * Created by Gina on 2/19/16.
 */
public class AthleteDbSchema {
    public static final class AthleteTable {
        public static final String NAME = "athlete";

        public static final class Cols {
            public static final String FIRSTNAME = "firstName";
            public static final String LASTNAME = "lastName";
            public static final String POSITION = "position";
            public static final String AGE = "age";
            public static final String YEARSPLAYED = "yearsPlayed";
            public static final String FEET = "feet";
            public static final String INCHES = "inches";
            public static final String WEIGHT = "weight";
            public static final String TWOKMIN = "twokMin";
            public static final String TWOKSEC = "twokSec";
            public static final String ID = "id";
        }
    }
}

