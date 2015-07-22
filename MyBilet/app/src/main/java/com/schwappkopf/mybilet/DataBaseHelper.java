package com.schwappkopf.mybilet;

/**
 * Created by schwappkopf on 9/29/14.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "bilet.db";
    private static final String TABLE_FIYAT = "Fiyat";

    private static final int DATABASE_VERSION = 1;

    // FIYAT Table - column names
    public static final String KEY_OGRENCI_BILET="ogrenci_bilet";
    private static final String KEY_TAM_BILET = "tam_bilet";
    private static final String KEY_AKTARMA = "aktarma";
    private static final String KEY_SECENEK = "secenek";
    private static final String KEY_BAKIYE="bakiye";
    // ID Table - column names
    private static final String KEY_FIYAT_ID = "fiyat_id";

    // Table Create Statements
    // FIYAT table create statement

    private static final String CREATE_TABLE_FIYAT = "CREATE TABLE "
            + TABLE_FIYAT + "(" + KEY_FIYAT_ID + " integer primary key autoincrement," + KEY_OGRENCI_BILET
            + " DOUBLE," + KEY_TAM_BILET + " DOUBLE," + KEY_AKTARMA + " DOUBLE," + KEY_SECENEK + " INTEGER,"
            + KEY_BAKIYE +" DOUBLE);";



    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_FIYAT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FIYAT);
        // create new tables
        onCreate(db);
    }

    public int createBilet(Bilet bilet) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] allColumns = { KEY_FIYAT_ID,KEY_OGRENCI_BILET,KEY_TAM_BILET,KEY_AKTARMA,KEY_SECENEK,KEY_BAKIYE};
        ContentValues values = new ContentValues();
        values.put(KEY_OGRENCI_BILET, bilet.getOgrenci_bilet());
        values.put(KEY_TAM_BILET, bilet.getTam_bilet());
        values.put(KEY_AKTARMA, bilet.getAktarma());
        values.put(KEY_SECENEK, bilet.getSecenek());
        values.put(KEY_BAKIYE, bilet.getBakiye());
        // insert row
        int fiyat_id = (int) db.insert(TABLE_FIYAT, null, values);
        //db.query() fonksiyonu daha hızlı sanki
        Cursor cursor = db.query(TABLE_FIYAT, allColumns, KEY_FIYAT_ID + " = " + fiyat_id, null,
                null, null, null);
        cursor.moveToLast();
        return fiyat_id;
    }

    /**
     * get single okunacak
     */
    public Bilet getFiyat(long fiyat_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        // String selectQuery = "SELECT  * FROM " + TABLE_FIYAT + " WHERE "
        //       + KEY_FIYAT_ID + " = " + fiyat_id;

        String selectQuery = "SELECT  * FROM " + TABLE_FIYAT;
        Cursor c = db.rawQuery(selectQuery, null);
        Bilet blt = new Bilet();
        if (c.moveToFirst())
        {

            blt.setId((c.getInt(c.getColumnIndex(KEY_FIYAT_ID))));
            blt.setOgrenci_bilet((c.getDouble(c.getColumnIndex(KEY_OGRENCI_BILET))));
            blt.setTam_bilet(c.getDouble(c.getColumnIndex(KEY_TAM_BILET)));
            blt.setAktarma((c.getDouble(c.getColumnIndex(KEY_AKTARMA))));
            blt.setSecenek((c.getInt(c.getColumnIndex(KEY_SECENEK))));
            blt.setBakiye((c.getDouble(c.getColumnIndex(KEY_BAKIYE))));

        }
        return blt;
    }

    /**
     * getting all okunacak
     * */
    public List<Bilet> getAllBilet() {
        List<Bilet> fiyatlar = new ArrayList<Bilet>();
        String selectQuery = "SELECT  * FROM " + TABLE_FIYAT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {

                Bilet blt = new Bilet();
                blt.setId((c.getInt(c.getColumnIndex(KEY_FIYAT_ID))));
                blt.setOgrenci_bilet((c.getDouble(c.getColumnIndex(KEY_OGRENCI_BILET))));
                blt.setTam_bilet(c.getDouble(c.getColumnIndex(KEY_TAM_BILET)));
                blt.setAktarma((c.getDouble(c.getColumnIndex(KEY_AKTARMA))));
                blt.setSecenek(c.getInt(c.getColumnIndex(KEY_SECENEK)));
                blt.setBakiye(c.getDouble(c.getColumnIndex(KEY_BAKIYE)));
                // adding to okunacak list
                fiyatlar.add(blt);
            } while (c.moveToNext());
        }

        return fiyatlar;
    }


    /**
     * getting okunacak count
     */
    public int getBiletCount() {
        String countQuery = "SELECT  * FROM " + TABLE_FIYAT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    /**
     * Updating a okunacak
     */
    public int updateBilet(Bilet blt,int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_OGRENCI_BILET, blt.getOgrenci_bilet());
        values.put(KEY_TAM_BILET, blt.getTam_bilet());
        values.put(KEY_AKTARMA, blt.getAktarma());
        values.put(KEY_SECENEK, blt.getSecenek());
        values.put(KEY_BAKIYE, blt.getBakiye());
        // updating row
        return db.update(TABLE_FIYAT, values, KEY_FIYAT_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    public int updateBakiye(Bilet blt,long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BAKIYE, blt.getBakiye());
        // updating row
        return db.update(TABLE_FIYAT, values, KEY_FIYAT_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    /**
     * Deleting a bilet
     */
    public void deleteBilet(long bilet_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FIYAT, KEY_FIYAT_ID + " = ?",
                new String[] { String.valueOf(bilet_id) });

        List<Bilet> values = getAllBilet();
        //Burda tabloyu tamamen silip yeniden oluşturuyorum çünkü hep 0. değeri çekiyorum
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FIYAT);
        db.execSQL(CREATE_TABLE_FIYAT);
//        db.execSQL("DELETE FROM " + tablename); deletes all row;
        for(int i=0; i<values.size(); i++)
        {
            createBilet(values.get(i));
        }

    }

}