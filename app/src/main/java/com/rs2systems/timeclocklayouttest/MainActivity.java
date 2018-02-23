package com.rs2systems.timeclocklayouttest;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    private int codePosition = 0;
    private String passCode = "";
    private Button mCheckedIN;
    private Button mCheckedOUT;

    private static final String SAMPLE_DB_NAME = "timeCard";
    private static final String SAMPLE_TABLE_NAME = "Info";


    /*public TextView code1 = (TextView) findViewById(R.id.textPassCode1);
    public TextView code2 = (TextView) findViewById(R.id.textPassCode2);
    public TextView code3 = (TextView) findViewById(R.id.textPassCode3);
    public TextView code4 = (TextView) findViewById(R.id.textPassCode4);*/


    //private m
    Map<String, String> m = new Map<String, String>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean containsKey(Object o) {
            return false;
        }

        @Override
        public boolean containsValue(Object o) {
            return false;
        }

        @Override
        public String get(Object o) {
            return null;
        }

        @Override
        public String put(String s, String s2) {
            return null;
        }

        @Override
        public String remove(Object o) {
            return null;
        }

        @Override
        public void putAll(@NonNull Map<? extends String, ? extends String> map) {

        }

        @Override
        public void clear() {

        }

        @NonNull
        @Override
        public Set<String> keySet() {
            return null;
        }

        @NonNull
        @Override
        public Collection<String> values() {
            return null;
        }

        @NonNull
        @Override
        public Set<Entry<String, String>> entrySet() {
            return null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DBHandler db = new DBHandler(this);

        getSupportActionBar().hide();
        DateFormat df2 = new SimpleDateFormat("EEEEEEE d MMM");
        Date dateobj2 = new Date();

        TextView tvFullDate = (TextView) findViewById(R.id.textClock2);
        tvFullDate.setText(df2.format(dateobj2));

        mCheckedIN = (Button) findViewById(R.id.btnCheckIN);
        mCheckedOUT = (Button) findViewById(R.id.btnCheckOUT);

        mCheckedIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView time = (TextView) findViewById(R.id.textViewTime);

                String ft = checkInOutTime();

                String currentDateTime[] = ft.split(" ");

                int intPassCode = Integer.parseInt(passCode);
                String employee = checkEmployee(intPassCode);
                if (employee.equals("notfound")) {
                    Toast.makeText(MainActivity.this,
                            "You entered wrong code. Press Clear Button", Toast.LENGTH_LONG).show();
                } else {
                    if (employee.equals("Robert Scott")) {
                        db.getAllEmployee();

                    } else {
                        long rowInserted = db.addEmployee(new Employee(employee, passCode, "checkin", currentDateTime[3], currentDateTime[1]));

                        if(rowInserted != -1) {
                            Toast.makeText(MainActivity.this, "New row added, row id: " + rowInserted, Toast.LENGTH_SHORT).show();
                            Toast.makeText(MainActivity.this, " " + ft + " " + employee + "  CHECKIN"
                                    , Toast.LENGTH_LONG).show();

                        }else
                            Toast.makeText(MainActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();

                    }

                }
            }

        });

        mCheckedOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date mcheckOut = new Date();

                TextView time = (TextView) findViewById(R.id.textViewTime);
                String ft = checkInOutTime();

                String currentDateTime[] = ft.split(" ");

                int intPassCode = Integer.parseInt(passCode);
                String employee = checkEmployee(intPassCode);
                if (employee.equals("notfound")) {
                    Toast.makeText(MainActivity.this,
                            "You entered wrong code. Press Clear Button", Toast.LENGTH_LONG).show();
                } else {
                    long rowInserted = db.addEmployee(new Employee(employee, passCode, "checkout", currentDateTime[3], currentDateTime[1]));

                    if(rowInserted != -1) {
                        Toast.makeText(MainActivity.this, "New row added, row id: " + rowInserted, Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, " " + ft + " " + employee + "  CHECKOUT"
                                , Toast.LENGTH_LONG).show();

                    }else {
                        Toast.makeText(MainActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();

                        //db.addEmployee(new Employee(employee, passCode, "checkout", currentDateTime[3], currentDateTime[1]));

                        Toast.makeText(MainActivity.this, " " + ft + " " + employee + "  CHECKOUT"
                                , Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }

    public void printDataBase() {

    }

    public String tableToString(SQLiteDatabase db, String tableName) {
        Log.d("", "tableToString called");
        String tableString = String.format("Table %s:\n", tableName);
        Cursor allRows = db.rawQuery("SELECT * FROM " + tableName, null);
        tableString += cursorToString(allRows);
        return tableString;
    }

    public String cursorToString(Cursor cursor) {
        String cursorString = "";
        if (cursor.moveToFirst()) {
            String[] columnNames = cursor.getColumnNames();
            for (String name : columnNames)
                cursorString += String.format("%s ][ ", name);
            cursorString += "\n";
            do {
                for (String name : columnNames) {
                    cursorString += String.format("%s ][ ",
                            cursor.getString(cursor.getColumnIndex(name)));
                }
                cursorString += "\n";
            } while (cursor.moveToNext());
        }
        return cursorString;
    }

    public String checkInOutTime() {
        Date mcheckIn = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd 'at' HH:mm");

        String reportDate = ft.format(mcheckIn);
        String rt = reportDate.toString();
        return rt;
    }

    public String checkEmployee(int checkCode) {
        int found = 0;
        String emp = "notfound";
        int code[] =
                {1234, 4976, 5115, 5948, 5450};
        String emps[] =
                {"Robert Scott", "Fred", "Marcus", "Kindra Edgestone", "Mashelle"};

        for (int i = 0; i < code.length; i++) {
            if (code[i] == checkCode) {
                found = 1;
                emp = emps[i];
            }
        }
        return emp;
    }

    public int submitChecking(String emp) {


        return 1;
    }

    public void numberPressed(View view) {
        Button b = (Button) view;
        String code = b.getText().toString();
        TextView code1 = (TextView) findViewById(R.id.textPassCode1);
        TextView code2 = (TextView) findViewById(R.id.textPassCode2);
        TextView code3 = (TextView) findViewById(R.id.textPassCode3);
        TextView code4 = (TextView) findViewById(R.id.textPassCode4);

        // add one to the positon to get the
        // correct location for the next code
        codePosition += 1;

        if (!code.equals("Del") && !code.equals("Clear")) {
            switch (codePosition) {
                case 1:
                    code1.setText(code);
                    passCode += code;
                    break;
                case 2:
                    code2.setText(code);
                    passCode += code;
                    break;
                case 3:
                    code3.setText(code);
                    passCode += code;
                    break;
                case 4:
                    code4.setText(code);
                    passCode += code;
                    break;
            }
        }

        if (code.equals("Clear")) {
            //Log.i("Clear", "starting the clear code...");
            code1.setText("");
            code2.setText("");
            code3.setText("");
            code4.setText("");
            codePosition = 0;
            passCode = "";
            //Log.i("Clear","exiting the clear code...");
        }

        if (code.equals("Del")) {
            // subtract one from position to
            // get the correct to remove the code
            codePosition -= 1;
            String msg = "";
            char[] myNameChars;
            TextView codePrint;

            switch (codePosition) {
                case 1:
                    code1.setText("");
                    codePosition -= 1;
                    // remove last code from the passCode file
                    msg = passCode;
                    myNameChars = msg.toCharArray();
                    myNameChars = Arrays.copyOfRange(myNameChars, 0, 0);
                    msg = String.valueOf(myNameChars);
                    codePrint = (TextView) findViewById(R.id.textViewCode);
                    codePrint.setText("");
                    codePrint.setText(msg.toString());
                    passCode = msg;

                    // remove last code from the passCode file
                    //int codeLength = passCode.length();
                    break;
                case 2:
                    code2.setText("");
                    codePosition -= 1;
                    // remove last code from the passCode file
                    msg = passCode;
                    myNameChars = msg.toCharArray();
                    myNameChars = Arrays.copyOfRange(myNameChars, 0, 1);
                    msg = String.valueOf(myNameChars);
                    codePrint = (TextView) findViewById(R.id.textViewCode);
                    codePrint.setText("");
                    codePrint.setText(msg.toString());
                    passCode = msg;
                    break;
                case 3:
                    code3.setText("");
                    codePosition -= 1;
                    // remove last code from the passCode file
                    msg = passCode;
                    myNameChars = msg.toCharArray();
                    myNameChars = Arrays.copyOfRange(myNameChars, 0, 2);
                    msg = String.valueOf(myNameChars);
                    codePrint = (TextView) findViewById(R.id.textViewCode);
                    codePrint.setText("");
                    codePrint.setText(msg.toString());
                    passCode = msg;
                    break;
                case 4:
                    code4.setText("");
                    codePosition -= 1;
                    // remove last code from the passCode file
                    msg = passCode;
                    myNameChars = msg.toCharArray();
                    myNameChars = Arrays.copyOfRange(myNameChars, 0, 3);
                    msg = String.valueOf(myNameChars);
                    codePrint = (TextView) findViewById(R.id.textViewCode);
                    codePrint.setText("");
                    codePrint.setText(msg.toString());
                    passCode = msg;
                    break;
            }
        }
    }
}
