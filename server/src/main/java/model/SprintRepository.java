package model;

import com.j256.ormlite.dao.Dao;
import model.dbo.Sprint;

/**
 * Created by salterok on 01.06.2015.
 */
public interface SprintRepository extends Dao<Sprint, Integer> {
    Sprint getCurrent();
    Sprint getPrevious();
}
