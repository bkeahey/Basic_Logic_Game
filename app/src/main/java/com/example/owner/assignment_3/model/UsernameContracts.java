package com.example.owner.assignment_3.model;

import android.provider.BaseColumns;

/**
 * Created by Owner on 4/3/2017.
 */

public class UsernameContracts {

    private UsernameContracts() {}

    public static class UsernameEntry implements BaseColumns {

        public static final String _ID = "id";
        public static final String COL_USERNAME = "username";
        public static  String COL_POINTS = "points";

    }
    public static final String TABLE_NAME = "Reg_table";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + "( " +
                    UsernameEntry._ID + " INTEGER PRIMARY KEY, " +
                    UsernameEntry.COL_USERNAME + " TEXT, " +
                    UsernameEntry.COL_POINTS + " INTEGER DEFAULT 0)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

}
