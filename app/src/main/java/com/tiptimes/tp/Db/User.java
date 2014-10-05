package com.tiptimes.tp.Db;

import com.tiptimes.tp.Db.annotation.Column;
import com.tiptimes.tp.Db.annotation.Table;

/**
 * Created by haoli on 14-10-4.
 */
@Table(name = "User")
public class User {
    @Column
    private int id;
    @Column
    private String name;

    public User(int id, String name){
        this.id = id;
        this.name = name;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
