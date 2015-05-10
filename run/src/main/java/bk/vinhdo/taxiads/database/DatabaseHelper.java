package bk.vinhdo.taxiads.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import bk.vinhdo.taxiads.config.DatabaseConfig;
import bk.vinhdo.taxiads.models.AddressModel;
import bk.vinhdo.taxiads.models.TimeOpenModel;
import bk.vinhdo.taxiads.models.UserModel;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private Dao<UserModel, String> userDao = null;
    private Dao<AddressModel, String> addressDao = null;
    private Dao<TimeOpenModel, String> timeOpenDao = null;

    private static DatabaseHelper instance;

    public DatabaseHelper(Context context) {
        super(context, DatabaseConfig.DATABASE_NAME, null,
                DatabaseConfig.DATABASE_VERSION);
    }

    public static DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, UserModel.class);
            TableUtils.createTable(connectionSource, AddressModel.class);
            TableUtils.createTable(connectionSource, TimeOpenModel.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            // after we drop the old databases, we create the new ones
            TableUtils.dropTable(connectionSource, UserModel.class, true);
            TableUtils.dropTable(connectionSource, AddressModel.class, true);
            TableUtils.dropTable(connectionSource, TimeOpenModel.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the Database Access Object (DAO) for our UserModel class. It will
     * create it or just give the cached value.
     *
     * @throws SQLException
     */
    public Dao<UserModel, String> getUserDao()
            throws SQLException {
        if (userDao == null) {
            userDao = getDao(UserModel.class);
        }
        return userDao;
    }

    /**
     * Returns the Database Access Object (DAO) for our AddressModel class. It will
     * create it or just give the cached value.
     *
     * @throws SQLException
     */
    public Dao<AddressModel, String> getAddressDao()
            throws SQLException {
        if (addressDao == null) {
            addressDao = getDao(AddressModel.class);
        }
        return addressDao;
    }

    /**
     * Returns the Database Access Object (DAO) for our UserModel class. It will
     * create it or just give the cached value.
     *
     * @throws SQLException
     */
    public Dao<TimeOpenModel, String> getTimeOpenDao()
            throws SQLException {
        if (timeOpenDao == null) {
            timeOpenDao = getDao(TimeOpenModel.class);
        }
        return timeOpenDao;
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        userDao = null;
        timeOpenDao = null;
        addressDao = null;
    }

}
