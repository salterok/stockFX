package model;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import model.dbo.Place;

import java.sql.SQLException;

/**
 * Created by salterok on 01.06.2015.
 */
public class PlaceRepositoryImpl extends BaseDaoImpl<Place, Integer> implements PlaceRepository {
    PlaceRepositoryImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Place.class);
    }
}
