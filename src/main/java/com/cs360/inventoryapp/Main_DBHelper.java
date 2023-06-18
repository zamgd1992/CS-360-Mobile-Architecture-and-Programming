package com.cs360.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Main_DBHelper extends SQLiteOpenHelper {

    private final Context context;
    private static final String DATABASE_NAME = "my_library.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_library";
    private static final String COLUMN_ID = "itemId";
    private static final String COLUMN_NAME = "itemName";
    private static final String COLUMN_DESCRIPTION = "itemDescription";
    private static final String COLUMN_QUANTITY = "itemQuantity";



    Main_DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // build a database
    @Override
    public void onCreate(SQLiteDatabase db) {

        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_DESCRIPTION + " TEXT, " +
                        COLUMN_QUANTITY + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // method to return all events to recycler view in home screen
    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);

        }
        return cursor;
    }

    // add an item to database
    long addItem(String itemName, String itemDescription, String itemQuantity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, itemName);
        cv.put(COLUMN_DESCRIPTION, itemDescription);
        cv.put(COLUMN_QUANTITY, itemQuantity);
        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1){
            Toast.makeText(context, "Failed to add", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Item added", Toast.LENGTH_SHORT).show();

        }
        return result;
    }


    void updateQuantity(String row_id, int quantity, int update) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        long result;

        if (update == 0) {
            quantity--;
        } else {
            quantity++;
        }

        if (quantity == 0) {
            assert context != null;
            HomeActivity.SendSMSMessage(context.getApplicationContext());
        }
        cv.put(COLUMN_QUANTITY, quantity);

        result = db.update(TABLE_NAME, cv, "itemId=?", new String[]{row_id});

        if (result == -1) {
            Toast.makeText(context, "Update failed", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(context, "Update success", Toast.LENGTH_SHORT).show();
        }
    }

    // delete a row / event if exists in the database
    public void deleteItem(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "itemId=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();

        }
    }

}


