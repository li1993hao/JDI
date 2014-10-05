package com.tiptimes.tp.Db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by haoli on 14-10-4.
 */
public interface DbListener {
    public void onCreate(SQLiteDatabase sqLiteDatabase);
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion);
    public int getVerson();
    public String getDataBaseName();

}
