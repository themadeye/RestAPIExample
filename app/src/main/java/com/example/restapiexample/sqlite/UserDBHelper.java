package com.example.restapiexample.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
    public static final String TABLE_CREDENTIAL = "credential";

    //Columns here
    public static final String ROW_ID = "row_id";
    public static final String ID = "id";
    public static final String EMAIL = "email";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";

    public static final String LOGIN_CREDENTIAL = "login_credential";

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
        db.execSQL(
                "create table credential " +
                        "(login_credential text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS credential");
        onCreate(db);
    }

    public void updateCredential(String credential){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LOGIN_CREDENTIAL, credential);
        db.update("credential", cv,null, null);
    }

    public String checkCredential(){
        String result="";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CREDENTIAL,
                new String[]{LOGIN_CREDENTIAL},
                null, null, null, null, null);
        try{
            if(cursor != null){
                if(cursor.moveToFirst()){
                    result = cursor.getString(0);
                    cursor.close();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return result;
    }

    public void insertUsers(Users users){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ID, users.getID());
        cv.put(EMAIL, users.getEmail());
        cv.put(FIRST_NAME, users.getFirstName());
        cv.put(LAST_NAME, users.getLastName());
        db.insert("users", null, cv);
    }

    public void updateUsers(Users users){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_USER);
        insertUsers(users);
    }

    public ArrayList<Users> getUsers(){
        ArrayList<Users> users = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER,
                new String[]{ID, EMAIL, FIRST_NAME, LAST_NAME},
                null, null, null, null, null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    Users u = new Users();
                    u.setID(cursor.getInt(0));
                    u.setEmail(cursor.getString(1));
                    u.setFirstName(cursor.getString(2));
                    u.setLastName(cursor.getString(3));
                    users.add(u);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();
        return users;
    }
}
