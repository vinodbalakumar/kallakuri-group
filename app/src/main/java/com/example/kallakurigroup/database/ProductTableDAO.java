package com.example.kallakurigroup.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kallakurigroup.models.productsmodels.ProductDetails;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.UpdateBuilder;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by android on 3/3/17.
 */

public class ProductTableDAO {
    public DatabaseHelper helper;
    Context mContext;
    protected SQLiteDatabase database;
    public ProductTableDAO(Context mContetx){
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
    public List<ProductDetails> getData()
    {
        //DatabaseHelper helper = new DatabaseHelper(mContext);
        RuntimeExceptionDao<ProductDetails, String> simpleDao = helper.getProductTableDataDao();
        List<ProductDetails> list = simpleDao.queryForAll();
        return list;
    }

    //method to get single product info
    public List<ProductDetails> getProductbyId(int pid)
    {
        List<ProductDetails> list = new ArrayList<>();
        //DatabaseHelper helper = new DatabaseHelper(mContext);
        RuntimeExceptionDao<ProductDetails, String> simpleDao = helper.getProductTableDataDao();
        try
        {
            //ProductDetailsTableModelNEW entity = simpleDao.queryForId(pid);
            // list = simpleDao.queryForEq("Product_Id",Integer.parseInt(pid));
            list  =   simpleDao.queryBuilder().where().eq("Product_Id",pid).query();

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

    //method to get single product info
    public List<ProductDetails> getProductByBrandId(String brandId)
    {
        List<ProductDetails> list = new ArrayList<>();
        //DatabaseHelper helper = new DatabaseHelper(mContext);
        RuntimeExceptionDao<ProductDetails, String> simpleDao = helper.getProductTableDataDao();
        try
        {
            //ProductDetailsTableModelNEW entity = simpleDao.queryForId(pid);
           // list = simpleDao.queryForEq("Product_Id",Integer.parseInt(pid));
            list  =   simpleDao.queryBuilder().where().eq("productBrandId",Integer.parseInt(brandId)).query();

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
    public int addData(ProductDetails resp)
    {
        int i = 0;
        try
        {
            RuntimeExceptionDao<ProductDetails, String> dao = helper.getProductTableDataDao();
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

    //method for insert data
    public int addData(List<ProductDetails> productsList)
    {
        int i = 0;
        try
        {
            RuntimeExceptionDao<ProductDetails, String> dao = helper.getProductTableDataDao();
            for(ProductDetails products : productsList)
            {

                ProductDetails productDetails = new ProductDetails(products.getId(), products.getProductName(), products.getProductCost(), products.getProductDescription(), products.getProductImage(), products.getCatalog(), products.getSubCatalog(), products.getStatus(), products.getProductDiscount(), products.getProductFinalPrice(), products.getProductType(), products.getProductCategory(), products.getProductBrand(), products.getProductBrandId(), products.getProductQuantity(), products.getProductDiscountAmount(), products.getDeliveryTime(), products.getDeliveryCharge(), "", "");
                i = dao.create(productDetails);

            }
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
        RuntimeExceptionDao<ProductDetails, String> dao = helper.getProductTableDataDao();
        UpdateBuilder<ProductDetails, String> updateBuilder = dao.updateBuilder();
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

    public int updateProduct(JSONObject jsonObject)
    {
        //   DatabaseHelper helper = new DatabaseHelper(mContext);
        RuntimeExceptionDao<ProductDetails, String> dao = helper.getProductTableDataDao();
        UpdateBuilder<ProductDetails, String> updateBuilder = dao.updateBuilder();

        int i =0;

        try{

            int Product_Id = 0;

            String ProductName = "", IsAgentOnlyProduct = "", Exclusive = "", ProductAttributes = "";

            String ProductBrand = "", ProductSchemes = "", ProductTargets = "", ProductImages = "";

            String ProductDescription = "", ProductDisclaimer = "", ProductVendorInfo = "", Profiles = "";

            String price = "", ImagePath = "";


            if(jsonObject.has("Product_Id")){
                Product_Id = Integer.parseInt(jsonObject.getString("Product_Id"));
                updateBuilder.updateColumnValue("Product_Id", Product_Id);

            }

            try {
                if (jsonObject.has("ProductName")) {
                    ProductName = jsonObject.getString("ProductName");
                    updateBuilder.updateColumnValue("ProductName", ProductName);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            try {

                if (jsonObject.has("IsAgentOnlyProduct")) {
                    IsAgentOnlyProduct = jsonObject.getString("IsAgentOnlyProduct");
                    updateBuilder.updateColumnValue("IsAgentOnlyProduct", IsAgentOnlyProduct);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            try{

            if(jsonObject.has("Exclusive")){
                Exclusive = String.valueOf(jsonObject.getJSONObject("Exclusive"));
                updateBuilder.updateColumnValue("Exclusive", Exclusive);
            }
            }catch (Exception e){
                e.printStackTrace();
            }

            try{

            if(jsonObject.has("ProductAttributes")){
                ProductAttributes = String.valueOf(jsonObject.getJSONObject("ProductAttributes"));
                updateBuilder.updateColumnValue("ProductAttributes", ProductAttributes);
            }
            }catch (Exception e){
                e.printStackTrace();
            }

            try{

            if(jsonObject.has("ProductBrand")){
                ProductBrand = String.valueOf(jsonObject.getJSONObject("ProductBrand"));
                updateBuilder.updateColumnValue("ProductBrand", ProductBrand);
            }
            }catch (Exception e){
                e.printStackTrace();
            }

            try{
            if(jsonObject.has("ProductSchemes")){
                ProductSchemes = String.valueOf(jsonObject.getJSONObject("ProductSchemes"));
                updateBuilder.updateColumnValue("ProductSchemes", ProductSchemes);
            }
            }catch (Exception e){
                e.printStackTrace();
            }

            try{

            if(jsonObject.has("ProductTargets")){
                ProductTargets = String.valueOf(jsonObject.getJSONObject("ProductTargets"));
                updateBuilder.updateColumnValue("ProductTargets", ProductTargets);
            }
            }catch (Exception e){
                e.printStackTrace();
            }

            try{

            if(jsonObject.has("ProductDescription")){
                ProductDescription = jsonObject.getString("ProductDescription");
                updateBuilder.updateColumnValue("ProductDescription", ProductDescription);
            }
            }catch (Exception e){
                e.printStackTrace();
            }

            try{

            if(jsonObject.has("ProductDisclaimer")){
                ProductDisclaimer = jsonObject.getString("ProductDisclaimer");
                updateBuilder.updateColumnValue("ProductDisclaimer", ProductDisclaimer);
            }
            }catch (Exception e){
                e.printStackTrace();
            }

            try{

            if(jsonObject.has("ProductVendorInfo")){
                ProductVendorInfo = String.valueOf(jsonObject.getJSONObject("ProductVendorInfo"));
                updateBuilder.updateColumnValue("ProductVendorInfo", ProductVendorInfo);
            }
            }catch (Exception e){
                e.printStackTrace();
            }

            try{

            if(jsonObject.has("Profiles")){
                Profiles = String.valueOf(jsonObject.getJSONObject("Profiles"));
                updateBuilder.updateColumnValue("Profiles", Profiles);
            }
            }catch (Exception e){
                e.printStackTrace();
            }

            try{

            if(jsonObject.has("price")){
                price = jsonObject.getString("price");
                updateBuilder.updateColumnValue("price", price);
            }
            }catch (Exception e){
                e.printStackTrace();
            }

            try{

            if(jsonObject.has("ImagePath")){
                ImagePath = jsonObject.getString("ImagePath");
                updateBuilder.updateColumnValue("ImagePath", ImagePath);
            }
            }catch (Exception e){
                e.printStackTrace();
            }

            try{
            if(jsonObject.has("ProductImages")){
                ProductImages = String.valueOf(jsonObject.getJSONArray("ProductImages"));
                updateBuilder.updateColumnValue("ProductImages", ProductImages);

            }
            }catch (Exception e){
                e.printStackTrace();
            }

            try{
                if(jsonObject.has("isActive")){
                    ProductImages = jsonObject.getString("isActive");
                    updateBuilder.updateColumnValue("isActive", ProductImages);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            updateBuilder.where().eq("Product_Id", Product_Id);
            i= dao.update(updateBuilder.prepare());
            Log.e("update resp pro",":"+i);

        }catch (Exception e){
            e.printStackTrace();
            i=0;
        }

        return i;
    }

    public int updateRow(String Tablename, ContentValues values, String key, int value)
    {
        RuntimeExceptionDao<ProductDetails, String> dao = helper.getProductTableDataDao();

        String condition = " Where "+" "+key+"="+ value ;

        int j =   dao.updateRaw("UPDATE "+Tablename +" set "+ values +condition);

        Log.v("ProductUpdate",""+j);

        return j;
    }

    //method for delete all rows
    public int deleteAll()
    {
        RuntimeExceptionDao<ProductDetails, String> dao = helper.getProductTableDataDao();
        List<ProductDetails> list = dao.queryForAll();
        int i = dao.delete(list);

        return i;
    }
}
