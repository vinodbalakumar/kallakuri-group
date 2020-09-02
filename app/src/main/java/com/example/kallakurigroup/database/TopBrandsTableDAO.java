package com.example.kallakurigroup.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.kallakurigroup.models.productsmodels.TopBrandsDetails;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.List;


/**
 * Created by android on 3/3/17.
 */

public class TopBrandsTableDAO {
    public DatabaseHelper helper;
    Context mContext;
    protected SQLiteDatabase database;
    public TopBrandsTableDAO(Context mContetx){
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
    public List<TopBrandsDetails> getData()
    {
        //DatabaseHelper helper = new DatabaseHelper(mContext);
        RuntimeExceptionDao<TopBrandsDetails, String> simpleDao = helper.getTopBrandTableDataDao();
        List<TopBrandsDetails> list = simpleDao.queryForAll();
        return list;
    }

    //method for insert data
    public int addData(TopBrandsDetails resp)
    {
        int i = 0;
        try
        {
            RuntimeExceptionDao<TopBrandsDetails, String> dao = helper.getTopBrandTableDataDao();
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
        RuntimeExceptionDao<TopBrandsDetails, String> dao = helper.getTopBrandTableDataDao();
        List<TopBrandsDetails> list = dao.queryForAll();
        int i = dao.delete(list);

        return i;
    }
}
