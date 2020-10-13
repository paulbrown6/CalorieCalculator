package com.pb.app.caloriecalculator.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.pb.app.caloriecalculator.api.RetrofitCall;
import com.pb.app.caloriecalculator.entity.AuthorisationEntity;

public class DatabaseSQL {

    private static DatabaseSQL instance;

    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private ContentValues contentValues;
    private boolean isDBSuccessful = false;

    private DatabaseSQL(){}

    public boolean writeUserDB(Context context) {
        dbHelper = new DBHelper(context);
        try {
            database = dbHelper.getWritableDatabase();
        }
        catch (SQLiteException ex){
            database = dbHelper.getReadableDatabase();
        }
        contentValues = new ContentValues();
        isDBSuccessful = false;
        Cursor cursor = database.query(DBHelper.TABLE_TYPES, null, null, null,
                null, null, null);
        if (cursor.moveToLast()) {
            isDBSuccessful = true;
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int tokenIndex = cursor.getColumnIndex(DBHelper.KEY_TOKEN);
            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_USER_NAME);
            AuthorisationEntity auth = new AuthorisationEntity();
            auth.setToken(cursor.getString(tokenIndex));
            auth.setUserName(cursor.getString(nameIndex));
            RetrofitCall.getInstance().setAuthorisationEntity(auth);
        }
        return isDBSuccessful;
    }

    public void readUserDb(Context context, String token, String username){
        dbHelper = new DBHelper(context);
        try {
            database = dbHelper.getWritableDatabase();
        }
        catch (SQLiteException ex){
            database = dbHelper.getReadableDatabase();
        }
        contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_TOKEN, token);
        contentValues.put(DBHelper.KEY_USER_NAME, username);
        database.insert(DBHelper.TABLE_TYPES, null, contentValues);
    }

    public boolean isDBSuccessful(){
        return isDBSuccessful;
    }

    public static DatabaseSQL getInstance(){
        if(instance == null) instance = new DatabaseSQL();
        return instance;
    }
}
