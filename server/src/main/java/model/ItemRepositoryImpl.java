package model;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import model.dbo.Item;

import java.sql.SQLException;

/**
 * Created by salterok on 01.06.2015.
 */
public class ItemRepositoryImpl extends BaseDaoImpl<Item, Integer> implements ItemRepository {
    ItemRepositoryImpl(ConnectionSource connection) throws SQLException {
        super(connection, Item.class);
    }
}
