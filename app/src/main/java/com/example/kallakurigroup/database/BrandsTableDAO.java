package com.example.kallakurigroup.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kallakurigroup.models.productsmodels.BrandsDetails;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.UpdateBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BrandsTableDAO {
    public DatabaseHelper helper;
    Context mContext;
    protected SQLiteDatabase database;
    public BrandsTableDAO(Context mContetx){
        this.mContext = mContetx;
        open();
    }
    public void open() throws android.database.SQLException
    {
        if(helper == null)
            helper = DatabaseHelper.getHelper(mContext);
        database = helper.getWritableDatabase();
    }

    //method for list of all products
    public List<BrandsDetails> getData()
    {
        //DatabaseHelper helper = new DatabaseHelper(mContext);
        RuntimeExceptionDao<BrandsDetails, String> simpleDao = helper.getBrandTableDataDao();
        List<BrandsDetails> list = simpleDao.queryForAll();
        return list;
    }

    //method for insert data
    public int addData(BrandsDetails resp)
    {
        int i = 0;
        try
        {
            RuntimeExceptionDao<BrandsDetails, String> dao = helper.getBrandTableDataDao();
            i = dao.create(resp);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        // Log.e("String add data",":"+i);
        /// DatabaseHelper helper = new DatabaseHelper(mContext);
        return i;
    }

    //method for delete all rows
    public int deleteAll()
    {
        RuntimeExceptionDao<BrandsDetails, String> dao = helper.getBrandTableDataDao();
        List<BrandsDetails> list = dao.queryForAll();
        int i = dao.delete(list);

        return i;
    }
}
