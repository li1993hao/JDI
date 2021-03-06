package com.tiptimes.tp.Db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.CancellationSignal;

import com.tiptimes.tp.Db.annotation.Ignore;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import haihemoive.Application;

/**
 * Created by haoli on 14-10-4.
 */
public class Dao<T extends Model> {
    public QueryBuilder<T> getFrom(Class cls) {
        QueryBuilder<T> sb = new QueryBuilder<T>();
        sb.cls = cls;
        return sb.setTable(cls.getSimpleName());

    }

    public int deleteAll(Class cls) {
        return delete(cls, null);
    }

    public int delete(Class cls, String whereClause, String... whereArgs) {
        DbHelper dh = new DbHelper(Application.getApplication());
        return dh.getWritableDatabase().delete(cls.getSimpleName(), whereClause, whereArgs);

    }

    public long insert(T object) {
        Map<Field, String> columnMap = getColumnMap(object.getClass());
        ContentValues contentValues = new ContentValues();

        Set<Field> filedSet = columnMap.keySet();
        for (Field field : filedSet) {
            String columnName = columnMap.get(field);
            try {
                setColumnValue(object, field, columnName, contentValues);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        DbHelper dh = new DbHelper(Application.getApplication());
        SQLiteDatabase db = dh.getWritableDatabase();

        return db.insert(object.getClass().getSimpleName(), null, contentValues);

    }

    public long insertAll(List<T> list) {
        Map<Field, String> columnMap = getColumnMap(list.get(0).getClass());
        Set<Field> filedSet = columnMap.keySet();
        String tableName= list.get(0).getClass().getSimpleName();

        int result = 0;

        DbHelper dh = new DbHelper(Application.getApplication());
        SQLiteDatabase db = dh.getWritableDatabase();
        db.beginTransaction();

        try {
            for (T object : list) {
                ContentValues contentValues = new ContentValues();
                for (Field field : filedSet) {
                    String columnName = columnMap.get(field);
                    setColumnValue(object, field, columnName, contentValues);
                }
                result = db.insert(tableName, null, contentValues)>0?result+1:result;
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            result = 0;
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }

        return result;
    }

    public int updateById(T object){

        Map<Field, String> columnMap = getColumnMap(object.getClass());
        Set<Field> filedSet = columnMap.keySet();

        ContentValues contentValues = new ContentValues();
        for (Field field : filedSet) {
            String columnName = columnMap.get(field);
            try {
                setColumnValue(object, field, columnName, contentValues);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        DbHelper dh = new DbHelper(Application.getApplication());
        SQLiteDatabase db = dh.getWritableDatabase();
        return db.update(object.getClass().getSimpleName(),contentValues,"_id=?",new String[]{object.get_id()+""});
    }



    public int update(Class<?> cls,ContentValues values, String whereClause, String... whereArgs){

        DbHelper dh = new DbHelper(Application.getApplication());
        SQLiteDatabase db = dh.getWritableDatabase();
        return db.update(cls.getSimpleName(),values,whereClause,whereArgs);
    }

    public class QueryBuilder<T> {
        private Class cls;
        private String table;
        private boolean distinct = false;
        private String[] columns;
        private String selection;
        private String[] selectionArgs;
        private String groupBy;
        private String having;
        private String orderBy;
        private String limit;
        private CancellationSignal cancellationSignal;

        public QueryBuilder<T> setTable(String table) {
            this.table = table;
            return this;
        }

        public QueryBuilder<T> setDistinct(boolean distinct) {
            this.distinct = distinct;
            return this;
        }

        public QueryBuilder<T> setColumns(String... columns) {
            this.columns = columns;
            return this;
        }

        public QueryBuilder<T> setSelection(String selection) {
            this.selection = selection;
            return this;
        }

        public QueryBuilder<T> setSelectionArgs(String... args) {
            this.selectionArgs = args;
            return this;
        }

        public QueryBuilder<T> setGroupBy(String groupBy) {
            this.groupBy = groupBy;
            return this;
        }

        public QueryBuilder setHaving(String having) {
            this.having = having;
            return this;
        }

        public QueryBuilder<T> setOrderBy(String orderBy) {
            this.orderBy = orderBy;
            return this;
        }

        public QueryBuilder<T> setLimit(String limit) {
            this.limit = limit;
            return this;
        }

        public QueryBuilder<T> setCancellationSignal(CancellationSignal cancellationSignal) {
            this.cancellationSignal = cancellationSignal;
            return this;
        }

        public List<T> all() {
            DbHelper dh = new DbHelper(Application.getApplication());
            Cursor cursor = dh.getReadableDatabase().query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit, cancellationSignal);
            List<T> list = new ArrayList<T>();
            Map<Field, String> columnMap = getColumnMap(cls);
            Set<Field> filedSet = columnMap.keySet();
            Field _id = null;
            try {
                 _id = Model.class.getDeclaredField("_id");
                _id.setAccessible(true);
            }catch (Exception e){
                e.printStackTrace();
            }

            while (cursor.moveToNext()) {
                try {
                    Object o = cls.newInstance();
                    _id.setLong(o,cursor.getLong(0));
                    for (Field field : filedSet) {
                        String columnName = columnMap.get(field);
                        setObjectValue(o, field, cursor, columnName);
                    }
                    list.add((T) o);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            cursor.close();
            return list;
        }

        public T one() {
            List<T> ls = all();
            if (ls.size() > 0) {
                return ls.get(0);
            } else {
                return null;
            }
        }

    }


    private Map<Field, String> getColumnMap(Class<?> cls) {
        Field[] fields = cls.getDeclaredFields();
        Map<Field, String> columnMap = new HashMap<Field, String>();
        for (int i = 0; i < fields.length; i++) {
            if (!fields[i].isAnnotationPresent(Ignore.class)) {
                columnMap.put(fields[i], fields[i].getName());
            }
        }
        return columnMap;
    }


    private void setColumnValue(Object o, Field field, String columnName, ContentValues contentValues) throws Exception {
        Class<?> filedType = field.getType();
        field.setAccessible(true);
        if (filedType.equals(int.class) || filedType.equals(Integer.class)) {
            contentValues.put(columnName, field.getInt(o));

        } else if (filedType.equals(boolean.class) || filedType.equals(Boolean.class)) {
            contentValues.put(columnName, field.getBoolean(o));

        } else if (filedType.equals(long.class) || filedType.equals(Long.class)) {

            contentValues.put(columnName, field.getLong(o));
        } else if (filedType.equals(float.class) || filedType.equals(Float.class)) {
            contentValues.put(columnName, field.getFloat(o));

        } else if (filedType.equals(double.class) || filedType.equals(Double.class)) {
            contentValues.put(columnName, field.getDouble(o));

        } else if (filedType.equals(short.class) || filedType.equals(Short.class)) {

            contentValues.put(columnName, field.getShort(o));
        } else if (filedType.equals(String.class)) {
            contentValues.put(columnName, (String) field.get(o));

        }
    }

    private void setObjectValue(Object o, Field field, Cursor cursor, String columnName) throws Exception {
        Class<?> filedType = field.getType();
        field.setAccessible(true);

        if (filedType.equals(int.class) || filedType.equals(Integer.class)) {

            field.setInt(o, cursor.getInt(cursor.getColumnIndex(columnName)));

        } else if (filedType.equals(boolean.class) || filedType.equals(Boolean.class)) {

            field.setBoolean(o, cursor.getInt(cursor.getColumnIndex(columnName)) == 0 ? false : true);

        } else if (filedType.equals(long.class) || filedType.equals(Long.class)) {

            field.setLong(o, cursor.getLong(cursor.getColumnIndex(columnName)));

        } else if (filedType.equals(float.class) || filedType.equals(Float.class)) {

            field.setFloat(o, cursor.getFloat(cursor.getColumnIndex(columnName)));

        } else if (filedType.equals(double.class) || filedType.equals(Double.class)) {

            field.setDouble(o, cursor.getDouble(cursor.getColumnIndex(columnName)));

        } else if (filedType.equals(short.class) || filedType.equals(Short.class)) {

            field.setShort(o, cursor.getShort(cursor.getColumnIndex(columnName)));

        } else if (filedType.equals(String.class)) {

            field.set(o, cursor.getString(cursor.getColumnIndex(columnName)));

        }
    }
}
