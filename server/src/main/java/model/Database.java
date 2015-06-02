package model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import model.dbo.Item;
import model.dbo.Place;
import model.dbo.RealItem;
import model.dbo.Sprint;

import javax.swing.*;
import java.sql.SQLException;
import java.util.concurrent.Callable;

/**
 * Created by salterok on 31.05.2015.
 */
public class Database {
    private static ConnectionSource connectionSource;
    public static void init(String url) {
        try {
            connectionSource = new JdbcPooledConnectionSource(url);

            places = new PlaceRepositoryImpl(connectionSource);
            realItems = new RealItemRepositoryImpl(connectionSource);
            items = new ItemRepositoryImpl(connectionSource);
            sprints = new SprintRepositoryImpl(connectionSource);

            TableUtils.createTableIfNotExists(connectionSource, Place.class);
            TableUtils.createTableIfNotExists(connectionSource, RealItem.class);
            TableUtils.createTableIfNotExists(connectionSource, Item.class);
            TableUtils.createTableIfNotExists(connectionSource, Sprint.class);
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void dispose() {
        try {
            connectionSource.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static <T> void transaction(Callable<T> action) {
        TransactionManager transactionManager = new TransactionManager(connectionSource);
        try {
            transactionManager.callInTransaction(action);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static SprintRepository sprints;
    public static ItemRepository items;
    public static RealItemRepository realItems;
    public static PlaceRepository places;
}
