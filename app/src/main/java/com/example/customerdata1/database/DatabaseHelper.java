package com.example.customerdata1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.customerdata1.model.Note;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "products_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Note.CREATE_TABLE);
        db.execSQL(Note.CREATE_TABLE1);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Note.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Note.TABLE_NAME1);

        // Create tables again
        onCreate(db);
    }

    public long insertNote(String name,String quantity,String price,String totalprice) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Note.PRODUCT_NAME, name);
        values.put(Note.PRODUCT_QUANTITY, quantity);
        values.put(Note.PRODUCT_PRICE, price);
        values.put(Note.PRODUCT_TOTAL_PRICE, totalprice);

        // insert row
        long id =db.insert(Note.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;

    }


    public long insertDetails(String size,String grandtotal) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them


        values.put(Note.SIZE, size);
        values.put(Note.GRAND_TOTAL_PRICE, grandtotal);


        // insert row
        long id = db.insert(Note.TABLE_NAME1, null, values);

        // close db connection
        db.close();

        // return newly inserted row id


      /*  long id = (long) db.insertWithOnConflict(Note.TABLE_NAME1, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        if (id == -1) {
            db.update(Note.TABLE_NAME1, values, Note.PRODUCT_ID1, new String[] {"1"});  // number 1 is the _id here, update to variable for your code

        }
        db.close();*/

        return id;

    }


    public Note getNote1(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Note.TABLE_NAME1,
                new String[]{Note.PRODUCT_ID, Note.SIZE, Note.GRAND_TOTAL_PRICE},
                Note.PRODUCT_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        assert cursor != null;
        Note note = new Note(
                cursor.getInt(cursor.getColumnIndex(Note.PRODUCT_ID)),
                cursor.getString(cursor.getColumnIndex(Note.SIZE)),
                cursor.getString(cursor.getColumnIndex(Note.GRAND_TOTAL_PRICE)));

        // close the db connection
        cursor.close();

        return note;
    }

    public Note getNote(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Note.TABLE_NAME,
                new String[]{Note.PRODUCT_ID, Note.PRODUCT_NAME, Note.PRODUCT_QUANTITY,Note.PRODUCT_PRICE, Note.PRODUCT_TOTAL_PRICE},
                Note.PRODUCT_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        assert cursor != null;
        Note note = new Note(
                cursor.getInt(cursor.getColumnIndex(Note.PRODUCT_ID)),
                cursor.getString(cursor.getColumnIndex(Note.PRODUCT_NAME)),
                cursor.getString(cursor.getColumnIndex(Note.PRODUCT_QUANTITY)),
                cursor.getString(cursor.getColumnIndex(Note.PRODUCT_PRICE)),
                cursor.getString(cursor.getColumnIndex(Note.PRODUCT_TOTAL_PRICE)));

        // close the db connection
        cursor.close();

        return note;
    }



    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Note.TABLE_NAME + " ORDER BY " +
                Note.PRODUCT_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setProduct_id(cursor.getInt(cursor.getColumnIndex(Note.PRODUCT_ID)));
                note.setProduct_name(cursor.getString(cursor.getColumnIndex(Note.PRODUCT_NAME)));
                note.setProduct_quantity(cursor.getString(cursor.getColumnIndex(Note.PRODUCT_QUANTITY)));
                note.setProduct_price(cursor.getString(cursor.getColumnIndex(Note.PRODUCT_PRICE)));
                note.setProduct_total_price(cursor.getString(cursor.getColumnIndex(Note.PRODUCT_TOTAL_PRICE)));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public List<Note> getAllNotes1() {
        List<Note> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Note.TABLE_NAME1 + " ORDER BY " +
                Note.PRODUCT_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setProduct_id(cursor.getInt(cursor.getColumnIndex(Note.PRODUCT_ID)));
                note.setSize(cursor.getString(cursor.getColumnIndex(Note.SIZE)));
                note.setGrand_total(cursor.getString(cursor.getColumnIndex(Note.GRAND_TOTAL_PRICE)));


                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + Note.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int getNotesCount1() {
        String countQuery = "SELECT  * FROM " + Note.TABLE_NAME1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Note.PRODUCT_NAME, note.getProduct_name());
        values.put(Note.PRODUCT_QUANTITY, note.getProduct_quantity());
        values.put(Note.PRODUCT_PRICE, note.getProduct_price());
        values.put(Note.PRODUCT_TOTAL_PRICE, note.getProduct_total_price());

        // updating row
        return db.update(Note.TABLE_NAME, values, Note.PRODUCT_ID + " = ?",
                new String[]{String.valueOf(note.getProduct_id())});
    }

    public void deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Note.TABLE_NAME, Note.PRODUCT_ID + " = ?",
                new String[]{String.valueOf(note.getProduct_id())});
        db.close();
    }
}
