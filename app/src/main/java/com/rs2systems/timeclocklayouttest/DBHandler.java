package com.rs2systems.timeclocklayouttest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robert on 10/19/2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "timeCard";

    // Employee table name
    private static final String TABLE_EMPLOYEE = "card";

    // Employee Table Columns names
    private static final String KEY_ID = "_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LOGIN_CODE = "login_code";
    private static final String KEY_CHECK_INOUT = "checkinInOut";
    private static final String KEY_TIME = "time";
    private static final String KEY_DATE = "date";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_EMPLOYEE_TABLE = "CREATE TABLE " + TABLE_EMPLOYEE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_LOGIN_CODE + " TEXT,"
                + KEY_CHECK_INOUT + " TEXT,"
                + KEY_TIME + " TEXT,"
                + KEY_DATE + " TEXT"
                + ")";
        sqLiteDatabase.execSQL(CREATE_EMPLOYEE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);

        // Create tables again
        onCreate(sqLiteDatabase);
    }

    // Adding new employee
    public void addEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, employee.get_name()); // employee Name
        values.put(KEY_LOGIN_CODE, employee.get_login_code()); // employee Phone Number
        values.put(KEY_CHECK_INOUT, employee.get_check_inout()); // employee check in or out
        values.put(KEY_TIME, employee.get_time()); // employee check in or out time
        values.put(KEY_DATE, employee.get_date()); // employee check in or out date

        // Inserting Row
        db.insert(TABLE_EMPLOYEE, null, values);
        db.close(); // Closing database connection
    }

    // Getting single employee
    public Employee getemployee(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EMPLOYEE, new String[]{KEY_ID,
                        KEY_NAME, KEY_LOGIN_CODE, KEY_CHECK_INOUT, KEY_TIME}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Employee employee = new Employee(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)
                , cursor.getString(5));
        // return employee
        return employee;
    }

    // Getting All Employee
    public List<Employee> getAllEmployee() {
        // Getting All Employee
        List<Employee> employeeList = new ArrayList<Employee>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EMPLOYEE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Employee employee = new Employee();
                employee.set_id(Integer.parseInt(cursor.getString(0)));
                employee.set_name(cursor.getString(1));
                employee.set_login_code(cursor.getString(2));
                employee.set_check_inout(cursor.getString(3));
                employee.set_time(cursor.getString(4));
                employee.set_date(cursor.getString(5));

                // Adding employee to list
                employeeList.add(employee);
            } while (cursor.moveToNext());
        }

        // return employee list
        return employeeList;
    }

    // Getting Employee Count
    public int getEmployeeCount() {
        String countQuery = "SELECT  * FROM " + TABLE_EMPLOYEE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Updating single employee
    public int updateemployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, employee.get_name());
        values.put(KEY_LOGIN_CODE, employee.get_login_code());
        values.put(KEY_CHECK_INOUT, employee.get_check_inout());
        values.put(KEY_TIME, employee.get_time());
        values.put(KEY_DATE, employee.get_date());

        // updating row
        return db.update(TABLE_EMPLOYEE, values, KEY_ID + " = ?",
                new String[]{String.valueOf(employee.get_id())});
    }

    // Deleting single employee
    public void deleteemployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMPLOYEE, KEY_ID + " = ?",
                new String[]{String.valueOf(employee.get_id())});
        db.close();
    }



}
