package com.example.mymodule.app2;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

public class MyClass {
    static public void main(String[] args) throws Exception{
        System.out.print(getCreateSql(B.class));
    }

    private static  String getCreateSql(Class<?> cls){
        StringBuilder sb = new StringBuilder("create table ");
        sb.append(cls.getSimpleName()).append(("(_id int primary autoincrement"));
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


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Ignore{

}

class  A{
    private int  _id;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
class  B extends  A{
    private String b;
    private String c;
    private int d;
    private boolean e;
}

