package com.rs2systems.timeclocklayouttest;

/**
 * Created by Robert on 10/19/2017.
 */

public class Employee {
    private int _id;
    private String _name;
    private String _login_code;
    private String _check_inout;
    private String _time;
    private String _date;

    public Employee() {

    }

    public Employee(int _id, String _name, String _login_code, String _check_inout, String _time, String _date) {
        this._id = _id;
        this._name = _name;
        this._login_code = _login_code;
        this._check_inout = _check_inout;
        this._time = _time;
        this._date = _date;
    }

    public Employee(String _name, String _login_code, String _check_inout, String _time, String _date) {
        this._name = _name;
        this._login_code = _login_code;
        this._check_inout = _check_inout;
        this._time = _time;
        this._date = _date;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_login_code() {
        return _login_code;
    }

    public void set_login_code(String _login_code) {
        this._login_code = _login_code;
    }

    public String get_check_inout() {
        return _check_inout;
    }

    public void set_check_inout(String _check_inout) {
        this._check_inout = _check_inout;
    }

    public String get_time() {
        return _time;
    }

    public void set_time(String _time) {
        this._time = _time;
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }
}
