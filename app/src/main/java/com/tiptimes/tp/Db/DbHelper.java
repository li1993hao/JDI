package com.tiptimes.tp.Db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by haoli on 14-10-4.
 */
public class DbHelper extends SQLiteOpenHelper {
    static private DbListener dl;

    public static void setDbListener(DbListener dl) {
        DbHelper.dl = dl;
    }

    public DbHelper(Context context){
        super(context, dl.getDataBaseName(), null, dl.getVerson());
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        dl.onCreate(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        dl.onUpgrade(sqLiteDatabase,oldVersion,newVersion);
    }
}
