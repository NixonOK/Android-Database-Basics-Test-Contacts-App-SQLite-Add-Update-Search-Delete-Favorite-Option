package com.w3e.nixonok.assignmentondbcontactsapp;

import java.io.Serializable;

/**
 * Created by NIXONOK on 2/19/2018.
 */

public class Contact implements Serializable {

    //private variables
    int _id;
    String _first_name;
    String _last_name;
    String _email;
    String _phone_number;
    String _number_type;
    String _favourite;

    // Empty constructor
    public Contact(){

    }

    // constructor
    public Contact(int _id, String _first_name, String _last_name, String _email, String _phone_number, String _number_type, String _favourite)
    {    this._id = _id;
         this._first_name = _first_name;
         this._last_name = _last_name;
         this._email = _email;
         this._phone_number = _phone_number;
         this._number_type = _number_type;
         this._favourite = _favourite;
    }


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_first_name() {
        return _first_name;
    }

    public void set_first_name(String _first_name) {
        this._first_name = _first_name;
    }

    public String get_last_name() {
        return _last_name;
    }

    public void set_last_name(String _last_name) {
        this._last_name = _last_name;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public String get_phone_number() {
        return _phone_number;
    }

    public void set_phone_number(String _phone_number) {
        this._phone_number = _phone_number;
    }

    public String get_number_type() {
        return _number_type;
    }

    public void set_number_type(String _number_type) {
        this._number_type = _number_type;
    }


    public String get_favourite() {
        return _favourite;
    }

    public void set_favourite(String _favourite) {
        this._favourite = _favourite;
    }
}