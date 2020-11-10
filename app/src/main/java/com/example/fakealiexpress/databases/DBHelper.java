package com.example.fakealiexpress.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "MyDB.db";
    private static final int SCHEMA = 1;
    private Context mContext;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


}
