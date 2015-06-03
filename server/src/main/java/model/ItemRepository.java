package model;

import com.j256.ormlite.dao.Dao;
import model.dbo.Item;
import model.dbo.Place;

import java.util.List;

/**
 * Created by salterok on 01.06.2015.
 */
public interface ItemRepository extends Dao<Item, Integer> {
    List<Item> getSprintItems(int sprintId);
}
