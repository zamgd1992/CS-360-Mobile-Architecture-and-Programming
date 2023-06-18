package com.cs360.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Login_DBHelper extends SQLiteOpenHelper {

    public Login_DBHelper(@Nullable Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        //create table with two columns, one of users and one of passwords
        MyDB.execSQL("create Table users(username TEXT primary key, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
    }


    // add new user and password
    public Boolean insertData(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = MyDB.insert("users", null, contentValues);
        return result != -1;

    }

    // check if username exists already
    public Boolean checkUsername(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        //check the db for user; if username exists return true
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[] {username});
        return cursor.getCount() > 0;
    }

    // check if password and username are paired correctly
    public Boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username, password});
        return cursor.getCount() > 0;
    }
}

