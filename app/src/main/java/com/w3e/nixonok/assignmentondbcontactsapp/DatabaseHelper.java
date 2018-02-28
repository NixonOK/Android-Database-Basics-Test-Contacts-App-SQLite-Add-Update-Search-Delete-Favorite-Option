package com.w3e.nixonok.assignmentondbcontactsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER24 on 2/19/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_PHONE_NO = "phone_number";
    private static final String KEY_EMAIL = "email_id";
    private static final String KEY_FAVOURITE = "favourite";
    private static final String KEY_NUMBER_TYPE = "number_type";


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_FIRST_NAME + " TEXT,"
                + KEY_LAST_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_PHONE_NO + " TEXT,"
                + KEY_NUMBER_TYPE + " TEXT,"
                + KEY_FAVOURITE + " TEXT"+ ")";
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(sqLiteDatabase);

    }

    // Adding new contact
    public boolean addContact(Contact contact) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_FIRST_NAME, contact.get_first_name());
        values.put(KEY_LAST_NAME, contact.get_last_name());
        values.put(KEY_EMAIL, contact.get_email());
        values.put(KEY_PHONE_NO, contact.get_phone_number());
        values.put(KEY_NUMBER_TYPE, contact.get_number_type());
        values.put(KEY_FAVOURITE, contact.get_favourite());


        long result = db.insert(TABLE_CONTACTS, null, values);

        if(result== -1){
            return false;

        }
        else
            return true;

    }


    // Getting All Contacts
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.set_id(Integer.parseInt(cursor.getString(0)));
                contact.set_first_name((cursor.getString(1)));
                contact.set_last_name(cursor.getString(2));
                contact.set_email(cursor.getString(3));
                contact.set_phone_number(cursor.getString(4));
                contact.set_number_type(cursor.getString(5));
                contact.set_favourite(cursor.getString(6));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // Getting All Contacts
    public List<Contact> searchMatchingContacts(String searchKey) {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT * FROM contacts where phone_number like '%"+searchKey+"%' or first_name like '%"+searchKey+"%' or last_name like '%"+searchKey+"%'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.set_id(Integer.parseInt(cursor.getString(0)));
                contact.set_first_name((cursor.getString(1)));
                contact.set_last_name(cursor.getString(2));
                contact.set_email(cursor.getString(3));
                contact.set_phone_number(cursor.getString(4));
                contact.set_number_type(cursor.getString(5));
                contact.set_favourite(cursor.getString(6));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // Updating single contact
    public boolean updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FIRST_NAME, contact.get_first_name()); // Contact Name
        values.put(KEY_LAST_NAME, contact.get_last_name()); // Contact Phone
        values.put(KEY_EMAIL, contact.get_email());
        values.put(KEY_PHONE_NO, contact.get_phone_number());
        values.put(KEY_NUMBER_TYPE, contact.get_number_type());
        values.put(KEY_FAVOURITE, contact.get_favourite());

        // updating row
        long result = db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.get_id()) });

        if(result== -1){
            return false;

        }
        else
            return true;
    }

    // Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.get_id()) });
        db.close();
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}
