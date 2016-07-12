package com.example.proj_beb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Влад on 29.06.2016.
 */

class SQLdb extends SQLiteOpenHelper {

    public SQLdb(Context context) {
        // конструктор суперкласса
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("LOG_TAG", "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("create table mytable2 ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "owe text,"
                + "value text,"
                + "currancy text,"
                + "dateOwe text,"
                + "dateBack text,"
                + "special text"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

