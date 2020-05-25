package com.example.mystore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "Customers.db";
    public static final String TABLE_NAME = "customer_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "FIRST_NAME";
    public static final String COL_3 = "LAST_NAME";
    public static  final String COL_4 = "ADDRESS";
    public static final String COL_5 = "AVG";
    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "FIRST_NAME TEXT,LAST_NAME TEXT,ADDRESS TEXT,AVG INTEGER)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion,
                          int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(Customer customer)
    {
        if(!checkExists(customer)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_2, customer.getName());
            contentValues.put(COL_3, customer.getLastName());
            contentValues.put(COL_4, customer.getCity());
            contentValues.put(COL_5, customer.getAvg());
            long result = db.insert(TABLE_NAME,
                    null, contentValues);
            // success !
            return result != -1; // fail !
        }
        else
            return false;
    }
    public boolean checkExists(Customer customer)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(
                "select * from "+TABLE_NAME+ " where FIRST_NAME="+"'"+customer.getName()+"'"+" AND LAST_NAME="+"'"+customer.getLastName()+"'"+" AND ADDRESS="+"'"+customer.getCity()+"'",
                null);
        if(res.getCount()>0)
            return true;
        else return false;
    }

    public boolean checkExistId(int nId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(
                "select * from "+TABLE_NAME+ " where ID="+"'"+nId+"'",
                null);
        return res.getCount() > 0;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(
                "select * from "+TABLE_NAME,
                null);
    }


    public boolean updateData(String id,Customer customer)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,customer.getName());
        contentValues.put(COL_3,customer.getLastName());
        contentValues.put(COL_4,customer.getCity());
        contentValues.put(COL_5,customer.getAvg());
        db.update(TABLE_NAME,
                contentValues, "ID = ?",
                new String[] { id });
        return true;
    }
    public Integer deleteData (String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,
                "ID = ?",new String[] {id});
    }

    public Cursor getLikeName(String strName) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(
                "select * from " + TABLE_NAME + " where " + COL_2 + " LIKE '%" + strName + "%' " +
                        "OR " + COL_3 + " LIKE '%" + strName + "%'",
                null);
    }

    public Cursor getMaxAvg(String strMinAvg) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(
                "select * from " + TABLE_NAME + " where " + COL_5 + " <= '" + strMinAvg + "'"
                        + " ORDER BY AVG DESC",
                null);
    }

    public Cursor getSameCustomer(String strFirstNmae, String strLastName) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(
                "select * from " + TABLE_NAME + " where " + COL_2 + "='" + strFirstNmae + "'" +
                        "AND " + COL_3 + " = '" + strLastName + "'",
                null);
    }

}
