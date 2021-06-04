package com.example.medicinereminder.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.medicinereminder.Model.MedicineModel;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    private static final String DATABASE_NAME = "MEDICINE_DATABASE";
    private static final String TABLE_NAME = "MEDICINE_TABLE";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "MEDICINE";
    private static final String COL_3 = "STATUS";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT , TASK TEXT , STATUS INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertMedicine(MedicineModel model) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2 , model.getMedicine());
        values.put(COL_3 , 0);
        db.insert(TABLE_NAME, null, values);
    }

    public void updateMedicine(int id , String medicine) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2 , medicine);
        db.update(TABLE_NAME, values, "ID=?" , new String[]{String.valueOf(id)});
    }

    public void updateStatus(int id, int status) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_3, status);
        db.update(TABLE_NAME, values, "ID=?", new String[]{String.valueOf(id)});
    }

    public void deleteMedicine(int id) {
        db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "ID=?", new String[]{String.valueOf(id)});
    }

    public List<MedicineModel> getAllMedicines() {

        db = this.getWritableDatabase();
        Cursor cursor = null;
        List<MedicineModel> modelList = new ArrayList<>();

        db.beginTransaction();
        try {
            cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        MedicineModel medicine = new MedicineModel();
                        medicine.setId(cursor.getInt(cursor.getColumnIndex(COL_1)));
                        medicine.setMedicine(cursor.getString(cursor.getColumnIndex(COL_2)));
                        medicine.setStatus(cursor.getInt(cursor.getColumnIndex(COL_3)));
                        modelList.add(medicine);
                    } while (cursor.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            cursor.close();
        }
        return modelList;
    }

}
