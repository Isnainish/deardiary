package com.isnaini.deardiary.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by root on 17/05/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "diary.db";
    public static final int DB_VERSION = 1;

    public static final String USER_TB_NAME = "tbuser";
    public static final String USER_COLUMN_ID = "id";
    public static final String USER_COLUMN_USERNAME = "username";
    public static final String USER_COLUMN_PASSWORD = "password";

    public static final String POST_TB_NAME = "tbpost";
    public static final String POST_COLUMN_ID = "id";
    public static final String POST_COLUMN_TITLE = "title";
    public static final String POST_COLUMN_DATE = "date";
    public static final String POST_COLUMN_MESSAGE = "message";


    public static final String CREATE_TBUSER = "CREATE TABLE " + USER_TB_NAME + " ("
            + USER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + USER_COLUMN_USERNAME + " VARCHAR(32) NOT NULL, "
            + USER_COLUMN_PASSWORD + " VARCHAR(32) NOT NULL)";

    public static final String CREATE_TBPOST = "CREATE TABLE " + POST_TB_NAME + " ("
            + POST_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + POST_COLUMN_TITLE + " VARCHAR(50) NOT NULL, "
            + POST_COLUMN_DATE + " DATETIME NOT NULL, "
            + POST_COLUMN_MESSAGE + " TEXT NOT NULL) ";



    public static final String DROP_TBUSER = "DROP TABLE IF EXIST" + USER_TB_NAME;

    public static final String DROP_TBPOST = "DROP TABLE IF EXIST" + POST_TB_NAME;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TBUSER);
        db.execSQL(CREATE_TBPOST);
    }

    public Cursor lihatUser(){
        SQLiteDatabase mDb = this.getWritableDatabase();
        Cursor mCursor = mDb.rawQuery("SELECT * FROM " + USER_TB_NAME, null);

        return mCursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHelper.class.getName(), "Upgrading Database From Old Version : "
                + oldVersion + " to : " + newVersion + ", REMOVE ALL DATA" );

        db.execSQL(DROP_TBUSER);
        db.execSQL(DROP_TBPOST);
        onCreate(db);
    }
}
