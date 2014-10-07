package com.tiptimes.tp.Db;

/**
 * Created by haoli on 14-10-4.
 */
public interface DbListener {
    public void onCreate(DbHelper dh);
    public void onUpgrade(DbHelper dh,int oldVersion, int newVersion);
    public int getVerson();
    public String getDataBaseName();

}
