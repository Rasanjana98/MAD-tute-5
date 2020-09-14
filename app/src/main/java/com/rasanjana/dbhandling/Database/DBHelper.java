package com.rasanjana.dbhandling.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static android.service.controls.ControlsProviderService.TAG;


public class DBHelper  extends SQLiteOpenHelper {
    public  static  final  String DATABASE_NAME ="UserInfo.db";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db ) {
        String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + UsersMaster.Users.TABLE_NAME+"("+
                        UsersMaster.Users._ID+"INTEGER PRIMARY KEY,"+
                        UsersMaster.Users.COLUMN_NAME_USERNAME+"TEXT,"+
                        UsersMaster.Users.COLUMN_NAME_USERNAME+"TEXT)";

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long addInfo(String userName,String password){
        SQLiteDatabase db =getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UsersMaster.Users.COLUMN_NAME_USERNAME,userName);
        values.put(UsersMaster.Users.COLUMN_NAME_PASSWORD,password);

        long  newRowId = db.insert(UsersMaster.Users.TABLE_NAME,null,values);
        return  newRowId;
    }

    public List readAllInfo(String req){
        SQLiteDatabase db= getReadableDatabase();

        String[] projection = {
                UsersMaster.Users._ID,
                UsersMaster.Users.COLUMN_NAME_USERNAME,
                UsersMaster.Users.COLUMN_NAME_PASSWORD
        };
        String sortOrder = UsersMaster.Users.COLUMN_NAME_USERNAME+ "DESC";

        Cursor cursor = db.query(
                UsersMaster.Users.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
        List userNames =new ArrayList<>();
        List passwords = new ArrayList<>();

        while (cursor.moveToNext()){
            String username = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_USERNAME));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_PASSWORD));
            userNames.add(username);
            passwords.add(password);
        }
        cursor.close();
        Log.i(TAG, "readAllInfo:" + userNames);
        if (req=="user"){
            return userNames;
        }else  if (req=="password"){
            return  passwords;
        }else {
            return  null;
        }
    }

    public  void deleteInfo (String userName){
        SQLiteDatabase db= getReadableDatabase();
        String selection = UsersMaster.Users.COLUMN_NAME_USERNAME + "LIKE ?";
        String [] selectionArgs = {userName};
        db.delete(UsersMaster.Users.TABLE_NAME,selection,selectionArgs);
    }
    public int updateInfo (String userName,String password){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(UsersMaster.Users.COLUMN_NAME_PASSWORD,password);

        String selection = UsersMaster.Users.COLUMN_NAME_USERNAME + "LIKE ?";
        String [] selectionArgs = {userName};

        int count = db.update(
                UsersMaster.Users.TABLE_NAME,
                values,
                selection,
                selectionArgs

        );

        return  count;
    }
}
