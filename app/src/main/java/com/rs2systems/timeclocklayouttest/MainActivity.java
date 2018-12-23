package com.rs2systems.timeclocklayouttest;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.String.valueOf;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int codePosition = 1;
    private String passCode = "";
    private Button mCheckedIN;
    private Button mCheckedOUT;
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8,
            btn9, btn0, btnClear, btnDel;


    private TextView code1;
    private TextView code2;
    private TextView code3;
    private TextView code4;


    DBHandler db;
    Intent chooser = null;

    private DateFormat df2;
    private Date dateobj2;
    private String ft;
    private String[] currentDateTime;

    private static final String SAMPLE_DB_NAME = "timeCard";
    private static final String SAMPLE_TABLE_NAME = "Info";

    private static final String FILE_NAME = "example.txt";

    private EditText mEditText;

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

        //final DBHandler db = new DBHandler(this);
        openDB();

        //clear action bar
        getSupportActionBar().hide();

        df2 = new SimpleDateFormat("EEEEEEE d MMM");
        dateobj2 = new Date();

        TextView tvFullDate = (TextView) findViewById(R.id.textClock2);
        tvFullDate.setText(df2.format(dateobj2));
        codePosition = 1;

        btn0 = (Button) findViewById(R.id.btn0);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btnClear = (Button) findViewById(R.id.btnClear);
        btnDel = (Button) findViewById(R.id.btnDel);

        mCheckedIN = (Button) findViewById(R.id.btnCheckIN);
        mCheckedOUT = (Button) findViewById(R.id.btnCheckOUT);


        //reg all btn with OnClickListener
        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnDel.setOnClickListener(this);

        mCheckedIN.setOnClickListener(this);
        mCheckedOUT.setOnClickListener(this);

        //Connect to security codes
        //
        code1 = (TextView) findViewById(R.id.textPassCode1);
        code2 = (TextView) findViewById(R.id.textPassCode2);
        code3 = (TextView) findViewById(R.id.textPassCode3);
        code4 = (TextView) findViewById(R.id.textPassCode4);

        TextView time;

    }

    private String populateListViewFromDB() {
        Cursor c = db.getAllRows();

        StringBuffer buffer = new StringBuffer();
        while (c.moveToNext()) {
            buffer.append("Id :" + c.getString(0));
            buffer.append(" " + c.getString(1));
            buffer.append(" " + c.getString(2));
            buffer.append(" " + c.getString(3));
            buffer.append(" " + c.getString(4));
            buffer.append(" " + c.getString(5) + "\n");
        }

        // Show all data
        //showMessage("Data",buffer.toString());
        return buffer.toString();
    }

    public void printDataBase() {

    }

    private void openDB() {
        db = new DBHandler(this);
        db.open();
    }

    private void closeDB() {
        db.close();
    }

    public void save(List<Employee> list) {

        boolean isAvailable = false;
        boolean isWritable = false;
        boolean isReadable = false;

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {

            // Read and write operation possible
            isAvailable = true;
            isWritable = true;
            isReadable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // Read operation possible
            isAvailable = true;
            isWritable = false;
            isReadable = true;
        } else {
            // SD card not mounted
            isAvailable = false;
            isWritable = false;
            isReadable = false;
        }

        String text = "This is a check to see if the file is being written...";

        File file = new File(getFilesDir(), "history.txt");

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Employee data;
        for (int i = 0; i < list.size(); i++) {

            data = list.get(i);
            String pnt = (data.get_id() + " " + data.get_login_code() + " "
                    + data.get_name() + " " + data.get_check_inout()
                    + " " + data.get_time() + " " + data.get_date());

            String filename = "filename.txt";
            file = new File(Environment.getExternalStorageDirectory(), filename);
            FileOutputStream fos;
            byte[] info = new String(pnt).getBytes();
            try {
                fos = new FileOutputStream(file);
                fos.write(info);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                // handle exception
            } catch (IOException e) {
                // handle exception
            }
        }

        Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILE_NAME,
                Toast.LENGTH_LONG).show();
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

    //TODO move employee info to Sqldb
    public String checkEmployee(int checkCode) {
        int found = 0;
        String emp = "notfound";
        int code[] =
                {7676, 4976, 5115, 5948, 5451, 4567};
        String emps[] =
                {"Robert Scott", "Fred", "Blank User", "Khadija ", "Schadwon", "Keisha"};

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

    /*public void numberPressed(View view) {


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
    }*/

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn0:
                //TODO: change to method call to set correct passcode textview
                setCodePosition(0);
//                Toast.makeText(MainActivity.this, "btn0 clicked",
//                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn1:
                setCodePosition(1);
//                Toast.makeText(MainActivity.this, "btn1 clicked",
//                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn2:
                setCodePosition(5);
//                Toast.makeText(MainActivity.this, "btn2 clicked",
//                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn3:
                setCodePosition(3);
//                Toast.makeText(MainActivity.this, "btn3 clicked",
//                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn4:
                setCodePosition(4);
//                Toast.makeText(MainActivity.this, "btn4 clicked",
//                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn5:
                setCodePosition(5);
//                Toast.makeText(MainActivity.this, "btn5 clicked",
//                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn6:
                setCodePosition(6);
//                Toast.makeText(MainActivity.this, "btn6 clicked",
//                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn7:
                setCodePosition(7);
//                Toast.makeText(MainActivity.this, "btn7 clicked",
//                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn8:
                setCodePosition(8);
//                Toast.makeText(MainActivity.this, "btn8 clicked",
//                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn9:
                setCodePosition(9);
//                Toast.makeText(MainActivity.this, "btn9 clicked",
//                        Toast.LENGTH_SHORT).show();
                break;


            case R.id.btnClear:
                code1.setText("");
                code2.setText("");
                code3.setText("");
                code4.setText("");
                codePosition = 1;
                passCode = "";
//                Toast.makeText(MainActivity.this, "Clear clicked",
//                        Toast.LENGTH_SHORT).show();
                break;
            //TODO: Delete is not working
            case R.id.btnDel:
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

                Toast.makeText(MainActivity.this, "Delete clicked",
                        Toast.LENGTH_SHORT).show();
                break;


            //TODO: complete the checkIN code
            case R.id.btnCheckIN:
                currentDateTime = setDateTime();

                int intPassCode = Integer.parseInt(passCode);
                String employee = checkEmployee(intPassCode);
                if (employee.equals("notfound")) {
                    Toast.makeText(MainActivity.this,
                            "You entered wrong code. Press Clear Button", Toast.LENGTH_LONG).show();
                } else {
                    if (employee.equals("Robert Scott")) {
                        startActivity(new Intent(MainActivity.this, DatabaseList.class));
                    } else {
                        long rowInserted = db.insertRow(employee, passCode, "checkin", currentDateTime[3], currentDateTime[1]);
                        //long rowInserted = db.insertRow(new Employee(employee, passCode, "checkin", currentDateTime[3], currentDateTime[1]));

                        if (rowInserted != -1) {
                            Toast.makeText(MainActivity.this, "New row added, row id: " + rowInserted, Toast.LENGTH_LONG).show();
                            Toast.makeText(MainActivity.this, " " + ft + " " + employee + "  CHECKIN"
                                    , Toast.LENGTH_LONG).show();
                            showMessage("You are loggedin","Please comfirm..." + "\n" + ft + " " + employee);

                            code1.setText("");
                            code2.setText("");
                            code3.setText("");
                            code4.setText("");
                            codePosition = 1;
                            passCode = "";

                        } else {
                            Toast toast = Toast.makeText(MainActivity.this, "Something wrong", Toast.LENGTH_SHORT);
                            v = (TextView) toast.getView().findViewById(android.R.id.message);
                            //v.setTextColor(Color.RED);
                            //((TextView) v).setTextColor("wentWrong");
                            toast.show();
                            //Toast.makeText(MainActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

           /*     Toast.makeText(MainActivity.this, "CheckIN clicked",
                        Toast.LENGTH_SHORT).show();*/
                break;


            case R.id.btnCheckOUT:
                currentDateTime = setDateTime();
                intPassCode = Integer.parseInt(passCode);
                employee = checkEmployee(intPassCode);
                if (employee.equals("notfound")) {
                    Toast.makeText(MainActivity.this,
                            "You entered wrong code. Press Clear Button", Toast.LENGTH_LONG).show();
                }
                if (employee.equals("Robert Scott")) {

                    String bufferData = populateListViewFromDB();
                    Intent myEmailIntend = new Intent(Intent.ACTION_SEND);
                    myEmailIntend.setData(Uri.parse("mailto:"));
                    String[] to =
                            {"rdscott1320@gmail.com", "rscott747@comcast.net"};
                    myEmailIntend.putExtra(Intent.EXTRA_EMAIL, to);
                    myEmailIntend.putExtra(Intent.EXTRA_SUBJECT, "Payroll data");
                    myEmailIntend.putExtra(Intent.EXTRA_TEXT, bufferData);
                    myEmailIntend.setType("message/rfc822");
                    chooser = Intent.createChooser(myEmailIntend, "Send Email");
                    startActivity(chooser);

                } else {
                    long rowInserted = db.insertRow(employee, passCode, "checkout", currentDateTime[3], currentDateTime[1]);

                    if (rowInserted != -1) {
                        Toast.makeText(MainActivity.this, "New row added, row id: " + rowInserted, Toast.LENGTH_LONG).show();
                        Toast.makeText(MainActivity.this, " " + ft + " " + employee + "  CHECKOUT"
                                , Toast.LENGTH_LONG).show();
                        showMessage("You are loggedout","Please comfirm..." + "\n" + ft + " " + employee);

                        code1.setText("");
                        code2.setText("");
                        code3.setText("");
                        code4.setText("");
                        codePosition = 1;
                        passCode = "";

                    } else {
                        Toast.makeText(MainActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();

                        db.insertRow(employee, passCode, "checkout", currentDateTime[3], currentDateTime[1]);

                        Toast.makeText(MainActivity.this, " " + ft + " " + employee + "  CHECKOUT"
                                , Toast.LENGTH_LONG).show();
                    }
                }

   /*             Toast.makeText(MainActivity.this, "CheckOUT clicked",
                        Toast.LENGTH_SHORT).show();*/
                break;
        }
    }

    //TODO: may need to change to a global variable-- FIX STILL NOT WORKING...
    private String[] setDateTime() {
        Date mcheckOut = new Date();

        TextView time = (TextView) findViewById(R.id.textViewTime);

        //time = (TextView) findViewById(R.id.textViewTime);
        ft = checkInOutTime();
        String currentDateTime[] = ft.split(" ");

        return currentDateTime;
    }

    private void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setIcon(R.drawable.icons8confirm30);
        builder.setTitle(title);
        builder.setMessage(Message);

        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // continue with delete
            }
        });
        builder.show();
    }

    //TODO: fix this code to set the correct passcode position
    //set passcode position
    //
    public void setCodePosition(int number) {
        // add one to the positon to get the
        // correct location for the next code

        switch (codePosition) {
            case 1:
                code1.setText(valueOf(number));
                passCode += number;
                codePosition += 1;
                break;
            case 2:
                code2.setText(valueOf(number));
                passCode += number;
                codePosition += 1;
                break;
            case 3:
                code3.setText(valueOf(number));
                passCode += number;
                codePosition += 1;
                break;
            case 4:
                code4.setText(valueOf(number));
                passCode += number;
                codePosition += 1;
                break;
        }
    }

}
