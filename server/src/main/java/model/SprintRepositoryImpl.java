package model;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import model.dbo.Sprint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

/**
 * Created by salterok on 01.06.2015.
 */
public class SprintRepositoryImpl extends BaseDaoImpl<Sprint, Integer> implements SprintRepository {
    private static final Logger logger = LogManager.getLogger(SprintRepositoryImpl.class);
    SprintRepositoryImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Sprint.class);
    }

    @Override
    public Sprint getPrevious() {
        try {
            QueryBuilder<Sprint, Integer> queryBuilder = this.queryBuilder()
                    .orderBy(Sprint.DATE_START, false).offset(1L).limit(1L);
            return queryBuilder.queryForFirst();
        } catch (SQLException ex) {
            logger.error(ex);
            return null;
        }
    }

    @Override
    public Sprint getCurrent() {
        QueryBuilder<Sprint, Integer> queryBuilder = this.queryBuilder()
                .orderBy(Sprint.DATE_START, false).limit(1L);
        try {
            return queryBuilder.queryForFirst();
        } catch (SQLException ex) {
            logger.error(ex);
            return null;
        }
    }
}
