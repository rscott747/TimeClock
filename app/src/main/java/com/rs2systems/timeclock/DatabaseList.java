package com.rs2systems.timeclock;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class DatabaseList extends Activity {

    DBHandler db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout for the layout we created
        setContentView(R.layout.databaselayout);

        // Get the Intent that called for this Activity to open
        Intent activityThatCalled = getIntent();

        openDB();
        populateListView();
    }

    private void openDB() {
        db = new DBHandler(this);
        db.open();
    }

    private void closeDB(){
        db.close();
    }

    private void populateListView() {
        Cursor cursor = db.getAllRows();
        String[] fromFieldNames = new String[]{DBHandler.KEY_ID, DBHandler.KEY_NAME,
                DBHandler.KEY_LOGIN_CODE, DBHandler.KEY_CHECK_INOUT, DBHandler.KEY_TIME,
                DBHandler.KEY_DATE};
        int[] toViewInIDS = new int[] {R.id.txtId, R.id.txtName, R.id.txtCode, R.id.txtInOut,
                R.id.txtTime, R.id.txtDate};
        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.itemdb,cursor,
                fromFieldNames, toViewInIDS,0);
        ListView myList = findViewById(R.id.listViewDb);
        myList.setAdapter(myCursorAdapter);
    }
}
