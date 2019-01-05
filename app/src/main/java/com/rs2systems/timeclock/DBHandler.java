package com.rs2systems.timeclock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Robert on 10/19/2017.
 */

public class DBHandler{

    // All Static variables
    // Database Version

    final ArrayList<String> row1 = new ArrayList<String>();
    final ArrayList<String> row2 = new ArrayList<String>();
    final ArrayList<String> row3 = new ArrayList<String>();
    final ArrayList<String> row4 = new ArrayList<String>();
    final ArrayList<String> row5 = new ArrayList<String>();
    final ArrayList<String> row6 = new ArrayList<String>();


    private static final String TAG = "DBHelper"; //used for logging database version changes

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "timeCard";

    // Employee table name
    private static final String TABLE_EMPLOYEE = "card";

    // Employee Table Columns names
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_LOGIN_CODE = "login_code";
    public static final String KEY_CHECK_INOUT = "checkinInOut";
    public static final String KEY_TIME = "time";
    public static final String KEY_DATE = "date";

    public static final String[] ALL_KEYS = new String[] {KEY_ID, KEY_NAME, KEY_LOGIN_CODE,
            KEY_CHECK_INOUT, KEY_TIME, KEY_DATE};

    // Column Numbers for each Field Name:
    public static final int COL_ROWID = 0;
    public static final int COL_NAME = 1;
    public static final int COL_LOGING_CODE = 2;
    public static final int COL_CHECK_INOUT = 3;
    public static final int COL_TIME = 4;
    public static final int COL_DATE = 5;

    /*******************TOP******************************/


    //SQL statement to create database
    private static final String DATABASE_CREATE_SQL =
            "CREATE TABLE " + TABLE_EMPLOYEE
                    + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_NAME + " TEXT NOT NULL, "
                    + KEY_LOGIN_CODE + " TEXT,"
                    + KEY_CHECK_INOUT + " TEXT,"
                    + KEY_TIME + " TEXT,"
                    + KEY_DATE + " TEXT"
                    + ");";

    private final Context context;
    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;


    public DBHandler(Context ctx) {
        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }

    // Open the database connection.
    public DBHandler open() {
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    // Close the database connection.
    public void close() {
        myDBHelper.close();
    }

    // Add a new set of values to be inserted into the database.
    public long insertRow(String name, String code, String inout, String time,  String date) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_LOGIN_CODE, code);
        initialValues.put(KEY_CHECK_INOUT, inout);
        initialValues.put(KEY_TIME, time);
        initialValues.put(KEY_DATE, date);

        // Insert the data into the database.
        return db.insert(TABLE_EMPLOYEE, null, initialValues);
    }

    // Delete a row from the database, by rowId (primary key)
    public boolean deleteRow(long rowId) {
        String where = KEY_ID + "=" + rowId;
        return db.delete(TABLE_EMPLOYEE, where, null) != 0;
    }

    public void deleteAll() {
        Cursor c = getAllRows();
        long rowId = c.getColumnIndexOrThrow(KEY_ID);
        if (c.moveToFirst()) {
            do {
                deleteRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    // Return all data in the database.
    public Cursor getAllRows() {
        String where = null;
        Cursor c = 	db.query(true, TABLE_EMPLOYEE, ALL_KEYS, null, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public String[] getAppCategoryDetail(Cursor cursor) {

/*        final String TABLE_NAME = "name of table";

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db  =
                db.getReadableDatabase();
        Cursor cursor      = db.rawQuery(selectQuery, null);
        String[] data      = null;*/

        String[] data      = null;
        //Cursor cc = db.rawQuery("SELECT * FROM datatable", null);
        Cursor cc = db.query(true, TABLE_EMPLOYEE, ALL_KEYS, null,
                null, null, null, null, null);


        if (cc.moveToFirst()) {
            do {
                // get the data into array, or class variable
                String number  = cursor.getString(0);
                String message = cursor.getString(1);
                String number1  = cursor.getString(2);
                String message1 = cursor.getString(3);
                String number2  = cursor.getString(4);
                String message2 = cursor.getString(5);
                row1.add(number);
                row2.add(message);
                row2.add(number1);
                row3.add(message1);
                row4.add(number2);
                row5.add(message2);
            } while (cc.moveToNext());
        }
        cc.close();
        return data;
    }



    // Change an existing row to be equal to new data.
    public boolean updateRow(long rowId, String name,  String code, String inout, String time,  String date) {
        String where = KEY_ID + "=" + rowId;
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_NAME, name); // employee Name
        newValues.put(KEY_LOGIN_CODE, code); // employee Phone Number
        newValues.put(KEY_CHECK_INOUT, inout); // employee check in or out
        newValues.put(KEY_TIME, time); // employee check in or out time
        newValues.put(KEY_DATE, date); // employee check in or out date
        // Insert it into the database.
        return db.update(TABLE_EMPLOYEE, newValues, where, null) != 0;
    }


    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DATABASE_CREATE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");

            // Destroy old database:
            _db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);

            // Recreate new database:
            onCreate(_db);
        }
    }

/*************************BOTTON*************************************/



    /**
     * Helper function that parses a given table into a string
     * and returns it for easy printing. The string consists of
     * the table name and then each row is iterated through with
     * column_name: value pairs printed out.
     *
     * @param db the database to get the table from
     * @param tableName the the name of the table to parse
     * @return the table tableName as a string
     */
    public String getTableAsString(SQLiteDatabase db, String tableName) {
        Log.d(TAG, "getTableAsString called");
        String tableString = String.format("Table %s:\n", tableName);
        Cursor allRows  = db.rawQuery("SELECT * FROM " + tableName, null);
        if (allRows.moveToFirst() ){
            String[] columnNames = allRows.getColumnNames();
            do {
                for (String name: columnNames) {
                    tableString += String.format("%s: %s\n", name,
                            allRows.getString(allRows.getColumnIndex(name)));
                }
                tableString += "\n";

            } while (allRows.moveToNext());
        }

        return tableString;
    }

}
