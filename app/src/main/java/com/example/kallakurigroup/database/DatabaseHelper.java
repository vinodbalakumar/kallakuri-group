package com.example.kallakurigroup.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kallakurigroup.models.productsmodels.BrandsDetails;
import com.example.kallakurigroup.models.productsmodels.ProductDetails;
import com.example.kallakurigroup.models.productsmodels.TopBrandsDetails;
import com.example.kallakurigroup.models.userModels.UserTableModel;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper
{

	private static final String DATABASE_NAME = "kallakuri.db";
	private static final int DATABASE_VERSION = 1;

	private Dao<UserTableModel, String> userTableDao = null;
	private RuntimeExceptionDao<UserTableModel, String> userTableRuntimeDao = null;

	private Dao<ProductDetails, String> ProductTableDao = null;
	private RuntimeExceptionDao<ProductDetails, String> productTableRuntimeDao = null;

	private Dao<BrandsDetails, String> brandTableDao = null;
	private RuntimeExceptionDao<BrandsDetails, String> brandTableRuntimeDao = null;

	private Dao<TopBrandsDetails, String> topBrandTableDao = null;
	private RuntimeExceptionDao<TopBrandsDetails, String> topBrandTableRuntimeDao = null;


	private static DatabaseHelper instance;

	public static synchronized DatabaseHelper getHelper(Context context)
    {
		if (instance == null)
			instance = new DatabaseHelper(context);
		return instance;
	}

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public Dao<UserTableModel, String> getUserTableDao() throws SQLException {
		if (userTableDao == null) {
			userTableDao = getDao(UserTableModel.class);
		}
		return userTableDao;
	}


	public RuntimeExceptionDao<UserTableModel, String> getUserTableDataDao() {
		if (userTableRuntimeDao == null) {
			userTableRuntimeDao = getRuntimeExceptionDao(UserTableModel.class);
		}
		return userTableRuntimeDao;
	}

	public Dao<BrandsDetails, String> getBrandTableDao() throws SQLException {
		if (brandTableDao == null) {
			brandTableDao = getDao(BrandsDetails.class);
		}
		return brandTableDao;
	}


	public RuntimeExceptionDao<BrandsDetails, String> getBrandTableDataDao() {
		if (brandTableRuntimeDao == null) {
			brandTableRuntimeDao = getRuntimeExceptionDao(BrandsDetails.class);
		}
		return brandTableRuntimeDao;
	}

	public Dao<TopBrandsDetails, String> getTopBrandTableDao() throws SQLException {
		if (topBrandTableDao == null) {
			topBrandTableDao = getDao(TopBrandsDetails.class);
		}
		return topBrandTableDao;
	}


	public RuntimeExceptionDao<TopBrandsDetails, String> getTopBrandTableDataDao() {
		if (topBrandTableRuntimeDao == null) {
			topBrandTableRuntimeDao = getRuntimeExceptionDao(TopBrandsDetails.class);
		}
		return topBrandTableRuntimeDao;
	}


	public Dao<ProductDetails, String> getProductTableDao() throws SQLException {
		if (ProductTableDao == null) {
			ProductTableDao = getDao(ProductDetails.class);
		}
		return ProductTableDao;
	}


	public RuntimeExceptionDao<ProductDetails, String> getProductTableDataDao() {
		if (productTableRuntimeDao == null) {
			productTableRuntimeDao = getRuntimeExceptionDao(ProductDetails.class);
		}
		return productTableRuntimeDao;
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		if (!db.isReadOnly()) {
			// Enable foreign key constraints
			db.execSQL("PRAGMA foreign_keys=ON;");
		}

		db.enableWriteAheadLogging();
	}


	@Override
	public void close() {
		super.close();
		userTableRuntimeDao = null;
		brandTableRuntimeDao = null;
		topBrandTableRuntimeDao = null;
		productTableRuntimeDao = null;
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onCreate");
			TableUtils.createTable(connectionSource, UserTableModel.class);
			TableUtils.createTable(connectionSource, BrandsDetails.class);
			TableUtils.createTable(connectionSource, TopBrandsDetails.class);
			TableUtils.createTable(connectionSource, ProductDetails.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onUpgrade");
			TableUtils.dropTable(connectionSource, UserTableModel.class, true);
			TableUtils.dropTable(connectionSource, BrandsDetails.class, true);
			TableUtils.dropTable(connectionSource, TopBrandsDetails.class, true);
			TableUtils.dropTable(connectionSource, ProductDetails.class, true);
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}
}