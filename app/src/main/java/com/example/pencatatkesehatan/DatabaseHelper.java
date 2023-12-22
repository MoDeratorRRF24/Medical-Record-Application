package com.example.pencatatkesehatan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE = "catatkesehatan.db";
    private static final String TABLE = "catat_kesehatan";
    public static final String COL_ID = "ID";
    public static final String COL_DATE = "date";
    public static final String COL_BERAT_BADAN = "berat_badan";
    public static final String COL_DETAK_JANTUNG = "detak_jantung";
    public static final String COL_KADAR_GULA = "kadar_gula";
    public static final String COL_TEKANAN_DARAH = "tekanan_darah";
    public static final String COL_KOLESTROL = "kolestrol";

    public DatabaseHelper(Context context) {
        super(context, DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE +
                " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_DATE + " TEXT, " +
                COL_BERAT_BADAN + " TEXT, " +
                COL_DETAK_JANTUNG + " TEXT, " +
                COL_KADAR_GULA + " TEXT, " +
                COL_TEKANAN_DARAH + " TEXT, " +
                COL_KOLESTROL + " TEXT)";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public boolean addData(String date, String beratBadan, String detakJantung, String kadarGula,
                           String tekananDarah, String kolestrol) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_DATE, date);
        contentValues.put(COL_BERAT_BADAN, beratBadan);
        contentValues.put(COL_DETAK_JANTUNG, detakJantung);
        contentValues.put(COL_KADAR_GULA, kadarGula);
        contentValues.put(COL_TEKANAN_DARAH, tekananDarah);
        contentValues.put(COL_KOLESTROL, kolestrol);

        long result = db.insert(TABLE, null, contentValues);
        return result != -1;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE, null);
    }

    public boolean deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE, COL_ID + " = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    public Cursor getDataById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + COL_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public boolean updateData(int id, String date, String beratBadan, String detakJantung,
                              String kadarGula, String tekananDarah, String kolestrol) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_DATE, date);
        contentValues.put(COL_BERAT_BADAN, beratBadan);
        contentValues.put(COL_DETAK_JANTUNG, detakJantung);
        contentValues.put(COL_KADAR_GULA, kadarGula);
        contentValues.put(COL_TEKANAN_DARAH, tekananDarah);
        contentValues.put(COL_KOLESTROL, kolestrol);

        int result = db.update(TABLE, contentValues, COL_ID + " = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }
}

