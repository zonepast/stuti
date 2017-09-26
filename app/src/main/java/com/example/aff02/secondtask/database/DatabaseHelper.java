package com.example.aff02.secondtask.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.aff02.secondtask.model.DetailModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AFF02 on 31-Aug-17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DETAILS";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "DETAILS";

    public static final String COL_ID = "_ID";
    public static final String COL_NAME = "User";
    public static final String COL_PASS = "Pass";
    public static final String COL_CONPASS = "Conpass";
    public static final String COL_EMAIL = "Email";
    public static final String COL_CONTACT = "Contact";
    public static final String COL_IDENTITY = "identity";


    SQLiteDatabase db;
    DetailModel detailModel = new DetailModel();

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME ,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String br = "CREATE TABLE "+TABLE_NAME+
                "("+COL_ID+ " INTEGER,"
                   +COL_NAME+ " TEXT PRIMARY KEY NOT NULL, " +COL_EMAIL+ " TEXT, "
                    +COL_PASS+ " TEXT, "+COL_CONPASS+" TEXT, "+COL_IDENTITY+" TEXT, " +COL_CONTACT+ " INTEGER);";
        db.execSQL(br);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME+" ;");
    }

    public long insertData (DetailModel detailModel)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String query = "select * from DETAILS";
        Cursor cursor = db.rawQuery(query,null);
        int count = cursor.getCount();

        contentValues.put(COL_ID,count);
        contentValues.put(COL_NAME,detailModel.getName());
        contentValues.put(COL_EMAIL,detailModel.getEmail());
        contentValues.put(COL_PASS,detailModel.getPass());
        contentValues.put(COL_CONPASS,detailModel.getConpass());
        contentValues.put(COL_CONTACT,detailModel.getContact());
        contentValues.put(COL_IDENTITY,detailModel.getIdentity());

        long result = db.insert(TABLE_NAME,null,contentValues);
        db.close();
        return result;
    }

    public List<DetailModel> getData()
    {
        String[] columns = {
                COL_ID,
                COL_NAME,
                COL_EMAIL,
                COL_PASS,
                COL_CONPASS,
                COL_CONTACT,
                COL_IDENTITY
        };

        List<DetailModel> detailModelsList = new ArrayList<DetailModel>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,columns,null,null,null,null,null);

        if (cursor.moveToFirst())
        {
            do {

                DetailModel detailModel = new DetailModel();
                detailModel.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COL_ID))));
                detailModel.setName(cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)));
                detailModel.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL)));
                detailModel.setPass(cursor.getString(cursor.getColumnIndexOrThrow(COL_PASS)));
                detailModel.setConpass(cursor.getString(cursor.getColumnIndexOrThrow(COL_CONPASS)));
                //detailModel.setContact(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COL_CONTACT))));
                detailModel.setIdentity(cursor.getString(cursor.getColumnIndexOrThrow(COL_IDENTITY)));
                detailModelsList.add(detailModel);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return detailModelsList;
    }

    public boolean checkUser(String email,String password,String identity)
    {
        String[] columns = {
                COL_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String query_l = "SELECT * FROM " + TABLE_NAME + " where " + COL_EMAIL + "='" + email + "'" +
                " AND " + COL_PASS + "='" + password + "'" + " AND " + COL_IDENTITY + "='" + identity + "'";
        //String selection = COL_EMAIL + " = ?" + " AND " + COL_PASS + " =?" + " WHERE " +COL_IDENTITY+ " = ?";
       // String[] selectionArgs = {email,password,identity};

        Cursor cursor = db.rawQuery(query_l,null);
        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();

        Log.e("Count",""+cursorCount);

        if (cursorCount>0)
        {
            return true;
        }
        return false;
    }

//    public boolean checkIdentity(String identity)
//    {
//        String[] columns = {
//                COL_ID
//        };
//        SQLiteDatabase db = this.getReadableDatabase();
//        //String selection = COL_EMAIL + " = ?" + " AND " + COL_PASS + " =?";
//        String selection = COL_IDENTITY + " = ?";
//        String[] selectionArgs = {identity};
//
//        Cursor cursor = db.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);
//        int cursorCount = cursor.getCount();
//
//        cursor.close();
//        db.close();
//
//        if (cursorCount>0)
//        {
//            return true;
//        }
//        return false;
//    }
//


    public boolean updatePass (String email,String newpass)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ID,"_ID");
        contentValues.put(COL_NAME,"User");
        contentValues.put(COL_EMAIL,"Email");
        contentValues.put(COL_PASS,"Pass");
        contentValues.put(COL_CONPASS,"Conpass");

        SQLiteDatabase db = this.getWritableDatabase();

//        db.update(TABLE_NAME, contentValues, COL_NAME + " = ?",
//                new String[] {detailModel.getName()});

        Cursor cur=db.rawQuery("UPDATE "+TABLE_NAME +" SET " + COL_PASS+ " = '"+newpass+"' WHERE "+ COL_EMAIL +"=?", new String[]{email});

        if (cur != null)
        {
            if(cur.getCount() > 0)
            {
                return true;
            }
        }
        return false;
    }

    public String getUsername() throws SQLException {
        String username = "";
        Cursor cursor = this.getReadableDatabase().query(
                TABLE_NAME, new String[] { COL_NAME },
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                username = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return username;
    }

}
