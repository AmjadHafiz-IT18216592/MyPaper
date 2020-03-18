package com.example.mypaper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
     //public static final String DATABASE_NAME = "Paper.db";
    //public static final String TABLE1_NAME = "User";
    //public static final int ID;
    //public static final String COL_1 = "EMAIL";
    //public static final String COL_2 = "PASSWORD";

    public DatabaseHelper(Context context) {
        super(context,"Paper.db", null , 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE User( EMAIL String PRIMARY KEY ,PASSWORD String  )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    db.execSQL("DROP TABLE IF EXISTS User");
    onCreate(db);
    }

    public boolean register(String email , String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL",email);
        contentValues.put("PASSWORD",password);

       long result =  db.insert("User",null, contentValues);

               if(result == -1)
                   return false;
               else
                   return true;

    }
    public Boolean checkEmail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE EMAIL =?", new String[]{email});
        if(cursor.getCount()>0) return  false;
        else return true;


    }
    public boolean login( String email,String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT PASSWORD FROM User WHERE EMAIL =?", new String[]{email});
        cursor.moveToFirst();
        String pw =  cursor.getString(0);
        if(password.equals(pw) )return  true;
        else return false;

    }
}
