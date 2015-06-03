package model;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import model.dbo.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by salterok on 01.06.2015.
 */
public class ItemRepositoryImpl extends BaseDaoImpl<Item, Integer> implements ItemRepository {
    private static final Logger logger = LogManager.getLogger(ItemRepositoryImpl.class);
    ItemRepositoryImpl(ConnectionSource connection) throws SQLException {
        super(connection, Item.class);
    }

    @Override
    public List<Item> getSprintItems(int sprintId) {
        try {
            return this.queryBuilder().where().eq(Item.SPRINT_ID, sprintId).query();
        }
        catch (Exception ex) {
            logger.error(ex);
            return Collections.emptyList();
        }
    }
}
