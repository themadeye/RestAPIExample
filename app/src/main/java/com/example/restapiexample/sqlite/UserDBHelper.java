package com.example.restapiexample.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.restapiexample.model.Users;

import java.util.ArrayList;

public class UserDBHelper extends SQLiteOpenHelper {

    //DB Name and version
    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;

    //Table
    public static final String TABLE_USER = "users";

    //Columns here
    public static final String ROW_ID = "row_id";
    public static final String ID = "id";
    public static final String EMAIL = "email";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";

    public UserDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(
                "create table users " +
                        "(row_id integer primary key, id integer, email text, first_name text, last_name text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    public void updateUsers(Users users){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ID, users.getID());
        cv.put(EMAIL, users.getEmail());
        cv.put(FIRST_NAME, users.getFirstName());
        cv.put(LAST_NAME, users.getLastName());
        db.insert("users", null, cv);

    }
}
