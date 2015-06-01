package model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import model.dbo.Item;
import model.dbo.Place;
import model.dbo.RealItem;
import model.dbo.Sprint;

import java.sql.SQLException;

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

    public static SprintRepository sprints;
    public static ItemRepository items;
    public static RealItemRepository realItems;
    public static PlaceRepository places;
}
