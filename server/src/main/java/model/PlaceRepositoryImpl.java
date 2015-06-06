package model;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import model.dbo.Item;
import model.dbo.Place;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static org.apache.logging.log4j.LogManager.getLogger;

/**
 * Created by salterok on 01.06.2015.
 */
public class PlaceRepositoryImpl extends BaseDaoImpl<Place, Integer> implements PlaceRepository {
    private static final Logger logger = getLogger(PlaceRepositoryImpl.class);
    PlaceRepositoryImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Place.class);
    }

    @Override
    public List<Place> getSprintPlaces(int sprintId) {
        try {
            return this.queryBuilder().where().eq(Place.SPRINT_ID, sprintId).query();
        }
        catch (Exception ex) {
            logger.error(ex);
            return Collections.emptyList();
        }
    }
}
