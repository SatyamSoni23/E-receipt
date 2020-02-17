package com.easybusiness.e_receipt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "e_receipt.db";
    public static final String TABLE_NAME = "e_receipt_shop_info";
    public static final String TABLE_NAME_IMAGE = "e_receipt_shop_logo";
    public static final String COL_1 = "shopName";
    public static final String COL_2 = "shopMobile";
    public static final String COL_3 = "shopAddress";
    public static final String COL_4 = "shopPincode";
    public static final String COL_5 = "shopEmail";
    public static final String COL_6 = "gstNumber";
    public static final String COL_7 = "slogan";
    public static final String COL_IMG_1 = "KEY_NAME";
    public static final String COL_IMG_2 = "KEY_IMAGE";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table IF NOT EXISTS " +TABLE_NAME + "(shopEmail TEXT PRIMARY KEY, shopName TEXT, shopMobile TEXT, shopAddress TEXT, shopPincode TEXT, gstNumber TEXT, slogan TEXT)"
        );
        db.execSQL("create table IF NOT EXISTS " +TABLE_NAME_IMAGE + "(KEY_NAME TEXT PRIMARY KEY, KEY_IMAGE BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String shopName, String shopMobile, String shopAddress, String shopPincode, String shopEmail, String gstNumber, String slogan){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_5, shopEmail);
        contentValues.put(COL_1, shopName);
        contentValues.put(COL_2, shopMobile);
        contentValues.put(COL_3, shopAddress);
        contentValues.put(COL_4, shopPincode);
        contentValues.put(COL_6, gstNumber);
        contentValues.put(COL_7, slogan);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }
/*
    public boolean updateData(String shopName, String shopMobile, String shopAddress, String shopPincode, String shopEmail, String gstNumber, String slogan){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, shopName);
        contentValues.put(COL_2, shopMobile);
        contentValues.put(COL_3, shopAddress);
        contentValues.put(COL_4, shopPincode);
        contentValues.put(COL_6, gstNumber);
        contentValues.put(COL_7, slogan);
        long result = db.update(TABLE_NAME, contentValues, "shopEmail="+shopEmail, null);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }


 */
    public boolean insertImage(String keyName, byte[] image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_IMG_1, keyName);
        contentValues.put(COL_IMG_2, image);
        long result = db.insert(TABLE_NAME_IMAGE, null, contentValues);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getAllData(String shopEmail){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from e_receipt_shop_info where shopEmail = '"+shopEmail+"'", null);
        return res;
    }

    public Cursor getInfo(String shopEmail){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from e_receipt_shop_info where shopEmail = '"+shopEmail+"'", null);
        return res;
    }

    public Cursor getImageInfo(String key_name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from e_receipt_shop_logo where KEY_NAME = '"+key_name+"'", null);
        return res;
    }
    public boolean updateData(String shopName, String shopMobile, String shopAddress, String shopPincode, String shopEmail, String gstNumber, String slogan){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, shopName);
        contentValues.put(COL_2, shopMobile);
        contentValues.put(COL_3, shopAddress);
        contentValues.put(COL_4, shopPincode);
        contentValues.put(COL_6, gstNumber);
        contentValues.put(COL_7, slogan);
        db.update(TABLE_NAME, contentValues, "shopEmail = ?", new String[] {shopEmail});
        return true;
    }

    public boolean updateImage(String keyName, byte[] image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_IMG_1, keyName);
        contentValues.put(COL_IMG_2, image);
        long result = db.update(TABLE_NAME_IMAGE, contentValues, "KEY_NAME="+keyName, null);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }
    public boolean deleteData (String shopEmail){
        SQLiteDatabase db = this.getWritableDatabase();
        boolean res = db.delete(TABLE_NAME, "shopEmail = ?", new String[] {shopEmail}) > 0;
        return res;
    }
}
