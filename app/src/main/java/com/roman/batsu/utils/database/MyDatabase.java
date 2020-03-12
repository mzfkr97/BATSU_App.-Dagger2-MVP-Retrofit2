package com.roman.batsu.utils.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.File;


public class MyDatabase extends SQLiteAssetHelper {

    //date('Y-m-d H:i:s','1299762201428')
    private static final String TAG = "TAG";
    private static final int DATABASE_VERSION = 1;
    private static String DATABASE_NAME = "batu_database.db";
    private static String TABLE_FAVORITES = "notes";
    private final Context context;
    private SQLiteDatabase myDataBase;

//    private static MyDatabase instance;
//
//    static synchronized MyDatabase getHelper(Context context)
//    {
//        if (instance == null)
//            instance = new MyDatabase(context);
//
//        return instance;
//    }

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        try {
            myDataBase = this.getWritableDatabase();
        } catch (SQLiteException e) {
            try {
                clearDB();
            } catch (Exception ex) {
                setForcedUpgrade();
            }
            Log.d(TAG, "MyDatabase: " + e);
        }
    }


    //  ПОЛУЧИТЬ все элементы
    public Cursor getAllData(String table_name) {
        myDataBase = getWritableDatabase();
        return myDataBase.query(table_name, null, null, null,
                null, null, null);
    }


    //  ПОЛУЧИТЬ все элементы остановок либо автобусов
    public Cursor getListStation(String station_id) {
        myDataBase = getReadableDatabase();
        String TABLE_NAME = "bus_end_new";
        String COLUMN_NAME = "bus_number";
        return myDataBase.rawQuery(
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + COLUMN_NAME +
                        " LIKE '" + station_id + "'" ,
                null);
    }

    //  ПОЛУЧИТЬ все элементы
    public Cursor getListFragmentStation(String station_id) {
        myDataBase = this.getReadableDatabase();
        String TABLE_NAME = "bus_number_time";
        String COLUMN_NAME = "station_id";
        return myDataBase.rawQuery(
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + COLUMN_NAME +
                        " LIKE '" + station_id + "'",
                null);
    }

    //  ПОЛУЧИТЬ все элементы автобусов в списке + расписание
    public Cursor getBusStation(String key_id_string) {
        myDataBase = this.getReadableDatabase();
        String TABLE_NAME = "bus_number_time";
        String KEY_ID = "key_id";
        String POSITION_SORT = "position_sort";
        return myDataBase.rawQuery(
                "SELECT * FROM " + TABLE_NAME
                        + " WHERE " + KEY_ID
                        + " LIKE '" + key_id_string + "'"
                        + " ORDER BY " + POSITION_SORT
                        + " ASC ", //сортировка
                null);
    }



    //  Поиск станции
    public Cursor findStation(String station_) {
        String TABLE_NAME = "station_list_mod";
        String COLUMN = "name";
        myDataBase = getReadableDatabase();
        return myDataBase.rawQuery(
                "SELECT * FROM " + TABLE_NAME
                        + " WHERE " + COLUMN
                        + " LIKE  '" + station_ + "'",
                null);
    }


    /**
     * Check favorite
     */
    public boolean checkBusInFavorites(String searchItem) {
        SQLiteDatabase database = getReadableDatabase();
        String[] columns = {"station_id"};
        String selection = "station_id" + " =?";
        String[] selectionArgs = {searchItem};
        String limit = "1";
        Cursor cursor = database.query(TABLE_FAVORITES,
            columns,
            selection,
            selectionArgs,
            null, null, null,
            limit);

        if (cursor.getCount() <= 0) {
            cursor.close();
            closeDB();
            return false;
        } else {
            cursor.close();
            closeDB();
            return true;
        }
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    /**
     * Insert row in
     * Favorite column
     */
    public void InsertStationToFavorites(String name,
                                         String to_station,
                                         String station_id) {
        SQLiteDatabase database = getReadableDatabase();
        ContentValues newValues = new ContentValues();

        newValues.put("name", name);
        newValues.put("to_station", to_station);
        newValues.put("station_id", station_id);
        database.insert(TABLE_FAVORITES, null, newValues);
        database.close();


    }

    /**
     * Delete database
     * row in Favorite column
     */
    public void deleteStationInFavorites(String position) {
        SQLiteDatabase database = getReadableDatabase();
        //myDataBase.delete("bus_station_favorites", null, null);  //this works
        //myDataBase.execSQL("delete from "+ "bus_station_favorites");  //too works
        database.delete(TABLE_FAVORITES,
                "station_id = ?",
                new String[]{position});
        database.close();
    }


    //  Поиск из таблицы медработников
    public Cursor findMedics(String key_) {
        String TABLE_NAME = "slcrb_data";
        String COLUMN = "key_query";
        myDataBase = getReadableDatabase();
        return myDataBase.rawQuery(
            "SELECT * FROM " + TABLE_NAME
                + " WHERE " + COLUMN
                + " LIKE  '" + key_ + "'",
            null);
    }




    /**
     * Clear database . call from preference fragment
     * clearDatabase()
     * { com.slutsk.roman.slutsktransp.preference.Fragment_Preference#clearDatabase() }
     */

    public void clearDB() {
        if (myDataBase != null && myDataBase.isOpen()) {
            myDataBase.close();
        }
        File file = new File(context.getDatabasePath(DATABASE_NAME).getPath());
        SQLiteDatabase.deleteDatabase(file);
    }
}