package model;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import model.dbo.RealItem;

import java.sql.SQLException;

/**
 * Created by salterok on 01.06.2015.
 */
public class RealItemRepositoryImpl extends BaseDaoImpl<RealItem, Integer> implements RealItemRepository {
    RealItemRepositoryImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, RealItem.class);
    }
}
