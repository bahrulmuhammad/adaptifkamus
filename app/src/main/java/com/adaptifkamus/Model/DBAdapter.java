package com.adaptifkamus.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hilmy on 14/06/2016.
 */
public class DBAdapter {

    static final String KEY_ROWID = "_id";
    static final String KW_ID = "kw_id";
    static final String KW_INA = "kw_ina";
    static final String KW_ARAB =  "kw_arab";
    static final String KW_PRIORITAS = "kw_prioritas";
    static final String KW_VERSI = "kw_versi";
    static final String KW_STATUS = "kw_status";
    static final String MEM_ID = "mem_id";
    static final String MEM_NOMOR = "mem_nomor";
    static final String  TAG = "DBAdapter";

    static final String DATABASE_NAME = "AdaptifKamus";
    static final String TABLE_KAMUS = "dataentry";
    static final String TABLE_GROUP = "grup";
    static final int DATABASE_VERSION = 1;

    static final String KAMUS_CREATE = "CREATE TABLE "+TABLE_KAMUS+" ("+KEY_ROWID+" integer auto increment, "+
            KW_ID+" integer PRIMARY KEY," + KW_INA+" text not null, "+KW_ARAB+" text not null, "+
            KW_PRIORITAS+" integer not null, "+KW_VERSI+" text not null" +
            KW_STATUS+" integer not null);";
    static final String GRUP_CREATE = "CREATE TABLE "+TABLE_GROUP+" ("+KEY_ROWID+" integer auto increment, "+
            MEM_ID+" integer PRIMARY KEY," +
            MEM_NOMOR+" text not null);";

    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DBAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            try {
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Memperbarui versi database dari " + oldVersion + " ke "
                    + newVersion);
            //db.execSQL("DROP TABLE IF EXIST kamus");
            //onCreate(db);
        }
    }

    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        DBHelper.close();
    }

    //memasukkan data entry ke tabel kamus
    public long insertKata(int id, String indo, String arab, int prioritas, String versi,
                           int status) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KW_ID, id);
        initialValues.put(KW_INA, indo);
        initialValues.put(KW_ARAB, arab);
        initialValues.put(KW_PRIORITAS, prioritas);
        initialValues.put(KW_VERSI, versi);
        initialValues.put(KW_STATUS, status);
        return db.insert(TABLE_KAMUS, null, initialValues);
    }

    public Cursor CariKataArab(String keyIndo) throws SQLException {
        Cursor mCursor  =
                db.query(true,  TABLE_KAMUS, new String[] {KW_ARAB}, KW_INA + " LIKE '" +
                                keyIndo+"'", null,null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;
    }

    public Cursor AmbilAllMember() throws SQLException {
        return db.query(TABLE_GROUP, new String[] {MEM_NOMOR}, null, null, null, null, null);
    }
}