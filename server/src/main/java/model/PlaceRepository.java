package model;

import com.j256.ormlite.dao.Dao;
import model.dbo.Place;

import java.util.List;

/**
 * Created by salterok on 01.06.2015.
 */
public interface PlaceRepository extends Dao<Place, Integer> {
    List<Place> getSprintPlaces(int sprintId);
}
