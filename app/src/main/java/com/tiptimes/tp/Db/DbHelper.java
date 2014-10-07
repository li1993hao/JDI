package com.tiptimes.tp.Db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tiptimes.tp.Db.annotation.Ignore;

import java.lang.reflect.Field;

/**
 * Created by haoli on 14-10-4.
 */
public class DbHelper extends SQLiteOpenHelper {
    static private DbListener dl;
    private  SQLiteDatabase sqLiteDatabase;

    public static void setDbListener(DbListener dl) {
        DbHelper.dl = dl;
    }

    public DbHelper(Context context){
        super(context, dl.getDataBaseName(), null, dl.getVerson());
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        this.sqLiteDatabase = sqLiteDatabase;
        dl.onCreate(this);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        this.sqLiteDatabase = sqLiteDatabase;
        dl.onUpgrade(this,oldVersion,newVersion);
    }



    public  <T extends Model>void creatTable(Class<T> cls){
        String sql = getCreateSql(cls);
        if(sqLiteDatabase != null){
            sqLiteDatabase.execSQL(sql);
        }else{
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(sql);
        }

    }

    public  <T extends Model>void dropTable(Class<T> cls){
        String sql = "drop table "+cls.getSimpleName();
        if(sqLiteDatabase != null){
            sqLiteDatabase.execSQL(sql);
        }else{
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(sql);
        }
    }


    private  String getCreateSql(Class<? extends  Model> cls){
        StringBuilder sb = new StringBuilder("create table ");
        sb.append(cls.getSimpleName()).append(("(_id integer primary key autoincrement"));
        Field[] fs = cls.getDeclaredFields();
        for(Field field:fs){
            if(!field.isAnnotationPresent(Ignore.class)){
                sb.append(",").append(field.getName());
            }
        }
        sb.append(")");
        return sb.toString();
    }

}
