package model;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import model.dbo.Sprint;

import java.sql.SQLException;

/**
 * Created by salterok on 01.06.2015.
 */
public class SprintRepositoryImpl extends BaseDaoImpl<Sprint, Integer> implements SprintRepository {

    SprintRepositoryImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Sprint.class);
    }
}
