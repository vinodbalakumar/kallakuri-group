package com.example.kallakurigroup.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.kallakurigroup.models.localdbmodels.UserTableModel;

public class Storage extends SQLiteOpenHelper {


    private static final String DB_NAME = "kk";
    private static final int VERSION = 1;

    public static final String USER_TABLE = "user";
    public static final String USER_UNO = "uno";
    public static final String USER_PHONE_NO = "phoneNo";
    public static final String USER_ROLE_NAME = "roleName";
    public static final String USER_ROLE_NUMBER = "roleNumber";
    public static final String USER_NAME = "name";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_VILLAGE = "village";
    public static final String USER_TOWN = "town";
    public static final String USER_DISTRICT = "district";
    public static final String USER_STATE = "state";
    public static final String USER_PINCODE = "pincode";
    public static final String USER_COLUMNS[] = {USER_UNO, USER_PHONE_NO, USER_ROLE_NAME, USER_ROLE_NUMBER, USER_NAME, USER_EMAIL, USER_PASSWORD, USER_VILLAGE, USER_TOWN, USER_DISTRICT, USER_STATE, USER_PINCODE};

    public Storage(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String USER_TABLE = "CREATE TABLE user (uno INT PRIMARY KEY, phoneNo TEXT, roleName TEXT," +
                "roleNumber TEXT, name TEXT, email TEXT, password TEXT, village TEXT," +
                "town TEXT, district TEXT, state TEXT, pincode TEXT)";
        sqLiteDatabase.execSQL(USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS user");
    }

    public void insertUserDetails(UserTableModel model) {

        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_UNO, 1);
        values.put(USER_PHONE_NO, model.getPhoneNo());
        values.put(USER_ROLE_NAME, model.getRoleName());
        values.put(USER_ROLE_NUMBER, model.getRoleNumber());
        values.put(USER_NAME, model.getName());
        values.put(USER_EMAIL, model.getEmail());
        values.put(USER_PASSWORD, model.getPassword());
        values.put(USER_VILLAGE, model.getVillage());
        values.put(USER_TOWN, model.getTown());
        values.put(USER_DISTRICT, model.getDistrict());
        values.put(USER_STATE, model.getState());
        values.put(USER_PINCODE, model.getPincode());

        database.insert(USER_TABLE, null, values);

        database.close();
    }

    public void updateUserDetails(UserTableModel model) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_PHONE_NO, model.getPhoneNo());
        values.put(USER_ROLE_NAME, model.getRoleName());
        values.put(USER_ROLE_NUMBER, model.getRoleNumber());
        values.put(USER_NAME, model.getName());
        values.put(USER_EMAIL, model.getEmail());
        values.put(USER_PASSWORD, model.getPassword());
        values.put(USER_VILLAGE, model.getVillage());
        values.put(USER_TOWN, model.getTown());
        values.put(USER_DISTRICT, model.getDistrict());
        values.put(USER_STATE, model.getState());
        values.put(USER_PINCODE, model.getPincode());
        database.update(USER_TABLE, values, "uno=1", null);

    }

    public UserTableModel getUserDetails() {
        SQLiteDatabase database = getReadableDatabase();
        UserTableModel model = new UserTableModel();

        Cursor cursor = database.query(USER_TABLE, USER_COLUMNS,null,null,null,null,null,null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                model.setUno(cursor.getInt(cursor.getColumnIndex(USER_UNO)));
                model.setPhoneNo(cursor.getString(cursor.getColumnIndex(USER_PHONE_NO)));
                model.setRoleName(cursor.getString(cursor.getColumnIndex(USER_ROLE_NAME)));
                model.setRoleNumber(cursor.getString(cursor.getColumnIndex(USER_ROLE_NUMBER)));
                model.setName(cursor.getString(cursor.getColumnIndex(USER_NAME)));
                model.setEmail(cursor.getString(cursor.getColumnIndex(USER_EMAIL)));
                model.setPassword(cursor.getString(cursor.getColumnIndex(USER_PASSWORD)));
                model.setVillage(cursor.getString(cursor.getColumnIndex(USER_VILLAGE)));
                model.setTown(cursor.getString(cursor.getColumnIndex(USER_TOWN)));
                model.setDistrict(cursor.getString(cursor.getColumnIndex(USER_DISTRICT)));
                model.setState(cursor.getString(cursor.getColumnIndex(USER_STATE)));
                model.setPincode(cursor.getString(cursor.getColumnIndex(USER_PINCODE)));
            }
        }

        return model;
    }


    public int deleteUser() {
        SQLiteDatabase database = getWritableDatabase();
        int status = database.delete(USER_TABLE,"uno=1", null);
        database.close();
        return status;
    }

}
