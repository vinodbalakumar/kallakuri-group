package com.example.kallakurigroup.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.example.kallakurigroup.models.userModels.UserTableModel;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.UpdateBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by android on 3/3/17.
 */

public class UserTableDAO {
    public DatabaseHelper helper;
    Context mContext;
    protected SQLiteDatabase database;
    public UserTableDAO(Context mContetx){
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
    public List<UserTableModel> getData()
    {
        //DatabaseHelper helper = new DatabaseHelper(mContext);
        RuntimeExceptionDao<UserTableModel, String> simpleDao = helper.getUserTableDataDao();
        List<UserTableModel> list = simpleDao.queryForAll();
        return list;
    }

    //method to get single product info
    public List<UserTableModel> getProductbyId(String pid)
    {
        List<UserTableModel> list = new ArrayList<>();
        //DatabaseHelper helper = new DatabaseHelper(mContext);
        RuntimeExceptionDao<UserTableModel, String> simpleDao = helper.getUserTableDataDao();
        try
        {
            //ProductDetailsTableModelNEW entity = simpleDao.queryForId(pid);
           // list = simpleDao.queryForEq("Product_Id",Integer.parseInt(pid));
            list  =   simpleDao.queryBuilder().where().eq("Product_Id",Integer.parseInt(pid)).and().eq("isActive", "1").query();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if(list.size()>0)
        {
            return list;
        }
        else
        {
            return null;
        }
    }

    //method for insert data
    public int addData(UserTableModel resp)
    {
        int i = 0;
        try
        {
            RuntimeExceptionDao<UserTableModel, String> dao = helper.getUserTableDataDao();
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

    public int updateRowByColName(int pid,String col_name)
    {
        int i = 0;
        RuntimeExceptionDao<UserTableModel, String> dao = helper.getUserTableDataDao();
        UpdateBuilder<UserTableModel, String> updateBuilder = dao.updateBuilder();
        try
        {
            updateBuilder.updateColumnValue(col_name,1);
            updateBuilder.where().eq("Product_Id", pid);
             i= dao.update(updateBuilder.prepare());
           //  Log.e("uploadstatus::"+col_name,"::"+i);
            return i;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return i;
        }
    }



    public int updateRow(String Tablename, ContentValues values, String key, String ColumnName)
    {
        RuntimeExceptionDao<UserTableModel, String> dao = helper.getUserTableDataDao();

        String condition = " Where "+" "+key+"="+ ColumnName ;

        int j =   dao.updateRaw("UPDATE "+Tablename +" set "+ values +condition);

        Log.v("ProductUpdate",""+j);

        return j;
    }

    //method for delete all rows
    public int deleteAll()
    {
        RuntimeExceptionDao<UserTableModel, String> dao = helper.getUserTableDataDao();
        List<UserTableModel> list = dao.queryForAll();
        int i = dao.delete(list);

        return i;
    }
}
