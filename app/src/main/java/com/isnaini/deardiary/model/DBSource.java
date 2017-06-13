package com.isnaini.deardiary.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.isnaini.deardiary.pojo.Diary;
import com.isnaini.deardiary.pojo.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by root on 17/05/17.
 */

public class DBSource {
    private SQLiteDatabase mSQLiteDatabase;
    private DBHelper mDBHelper;

    SimpleDateFormat mFormat = new SimpleDateFormat("EEE, dd MMM yyyy");

    private String[] columnTBUser = {
            mDBHelper.USER_COLUMN_ID,
            mDBHelper.USER_COLUMN_USERNAME,
            mDBHelper.USER_COLUMN_PASSWORD
    };

    private String[] columnTBPost = {
            mDBHelper.POST_COLUMN_ID,
            mDBHelper.POST_COLUMN_TITLE,
            mDBHelper.POST_COLUMN_DATE,
            mDBHelper.POST_COLUMN_MESSAGE
    };

    public DBSource(Context context) {
        mDBHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        mSQLiteDatabase = mDBHelper.getWritableDatabase();
    }

    public void close() {
        mDBHelper.close();
    }

    public User insertUser(String username, String password){
        ContentValues mContentValues = new ContentValues();

        mContentValues.put(mDBHelper.USER_COLUMN_USERNAME, username);
        mContentValues.put(mDBHelper.USER_COLUMN_PASSWORD, password);

        long insertUserID = mSQLiteDatabase.insert(mDBHelper.USER_TB_NAME,
                null, mContentValues);

        Cursor mCursor = mSQLiteDatabase.query(mDBHelper.USER_TB_NAME,
                columnTBUser, mDBHelper.USER_COLUMN_ID + " = "
                        + insertUserID, null, null, null, null);

        mCursor.moveToFirst();

        User newUser = cursorToUser(mCursor);

        mCursor.close();

        return newUser;
    }

    public Diary insertPost(String title, String date, String message) throws ParseException {
        ContentValues mContentValues = new ContentValues();

        mContentValues.put(mDBHelper.POST_COLUMN_TITLE, title);
        mContentValues.put(mDBHelper.POST_COLUMN_DATE, date);
        mContentValues.put(mDBHelper.POST_COLUMN_MESSAGE, message);

        long insertPostID = mSQLiteDatabase.insert(mDBHelper.POST_TB_NAME,
                null, mContentValues);

        Cursor mCursor = mSQLiteDatabase.query(mDBHelper.POST_TB_NAME,
                columnTBPost, mDBHelper.POST_COLUMN_ID + " = "
                + insertPostID, null, null, null,null);

        mCursor.moveToFirst();

        Diary newPost = cursorToPost(mCursor);

        mCursor.close();

        return newPost;
    }

    private User cursorToUser(Cursor mCursor) {
        User mUser = new User();

        Log.v("info", "The getLONG " + mCursor.getLong(0));
        Log.v("info", "The SetLATLNG " + mCursor.getString(1) +"," + mCursor.getString(2));

        mUser.setId(mCursor.getLong(0));
        mUser.setUsername(mCursor.getString(1));
        mUser.setPassword(mCursor.getString(2));

        Log.v("info", "Username Test " + mUser.getUsername() );

        return mUser;
    }

    private Diary cursorToPost(Cursor mCursor) throws ParseException {
        Diary mPost = new Diary();

        Log.v("info", "The getLONG" + mCursor.getLong(0));
        Log.v("info", "The getString" + mCursor.getString(1) +" , "
                + mCursor.getString(2) +" , "
                + mCursor.getString(3));

//        Date mDate = mFormat.parse(mCursor.getString(2));

        mPost.setId(mCursor.getLong(0));
        mPost.setTitle(mCursor.getString(1));
        mPost.setDate(mFormat.parse(mCursor.getString(2)));
        mPost.setMessage(mCursor.getString(3));

        Log.v("info", "Post Test" + mPost.getTitle());

        return mPost;
    }

    public User getOneUser(){

        Cursor mCursor = mSQLiteDatabase.query(DBHelper.USER_TB_NAME, columnTBUser,
                null,null,null,null,null);

        mCursor.moveToFirst();

        User loginUser = cursorToUser(mCursor);

        mCursor.close();

        return loginUser;

    }

    public Cursor checkTBUser(){

        Cursor mCursor = mSQLiteDatabase.query(DBHelper.USER_TB_NAME, columnTBUser,
                null,null,null,null,null);

        return mCursor;
    }

    public ArrayList<Diary> getAllDiary() throws ParseException {
        ArrayList<Diary> daftarDiary = new ArrayList<Diary>();

        Cursor mCursor = mSQLiteDatabase.query(DBHelper.POST_TB_NAME,columnTBPost,
                null, null, null, null, DBHelper.POST_COLUMN_ID + " DESC");

        mCursor.moveToFirst();

        while (!mCursor.isAfterLast()){
            Diary mPost = cursorToPost(mCursor);
            daftarDiary.add(mPost);
            mCursor.moveToNext();
        }

        mCursor.close();

        return daftarDiary;
    }

    public Diary getOneDiary(Long id) throws ParseException {
        Diary mDiary = new Diary();

        Cursor mCursor = mSQLiteDatabase.query(DBHelper.POST_TB_NAME,columnTBPost,"id = " + id, null, null, null, null);

        mCursor.moveToFirst();

        mDiary = cursorToPost(mCursor);
        mCursor.close();

        return mDiary;
    }

    public void deletePost(Long id){
        String strFilter = "id = " + id;
        mSQLiteDatabase.delete(DBHelper.POST_TB_NAME, strFilter, null);
    }

    public Diary updatePost(String id, String title, String date, String message) throws ParseException {
        String strFilter = "id = " + id;

        ContentValues mContentValues = new ContentValues();

        mContentValues.put(DBHelper.POST_COLUMN_TITLE, title);
        mContentValues.put(DBHelper.POST_COLUMN_DATE, date);
        mContentValues.put(DBHelper.POST_COLUMN_MESSAGE, message);

        long updatePostID = mSQLiteDatabase.update(DBHelper.POST_TB_NAME, mContentValues, strFilter, null);

        Cursor mCursor = mSQLiteDatabase.query(mDBHelper.POST_TB_NAME,
                columnTBPost, mDBHelper.POST_COLUMN_ID + " = "
                        + id, null, null, null,null);

        mCursor.moveToFirst();

        Diary updatePost = cursorToPost(mCursor);

        mCursor.close();

        return updatePost;
    }

}
