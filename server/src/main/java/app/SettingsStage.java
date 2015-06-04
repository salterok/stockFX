package app;

import model.Database;
import model.dbo.Item;
import model.dbo.Place;
import model.dbo.Sprint;
import pojo.LocalConfig;

import java.io.File;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by salterok on 02.06.2015.
 */
public class SettingsStage {
    private Sprint prevSprint;
    private Sprint currSprint;

    public void init() {
        prevSprint = Database.sprints.getPrevious();
        currSprint = Database.sprints.getCurrent();
    }

    public List<Place> getCurrentPlaces() {
        return Database.places.getSprintPlaces(currSprint.id);
    }

    public List<Place> getPreviousPlaces() {
        if (prevSprint == null) {
            return Collections.emptyList();
        }
        return Database.places.getSprintPlaces(prevSprint.id);
    }

    public void getItemsToGenerateQR(LocalConfig.SettingStageResult settingStageResult) {
        List<Item> items = Database.items.getSprintItems(currSprint.id);
        List<Item> prevItems = prevSprint == null ?
                Collections.emptyList() :
                Database.items.getSprintItems(prevSprint.id);
        settingStageResult.allItems = items;

        Map<Long, Item> map = items.stream().collect(Collectors.toMap(i -> i.invNumber, i -> i));
        for (Item item : prevItems) {
            map.remove(item.invNumber);
        }

        settingStageResult.toGenQR = new ArrayList<>(map.values());
    }

    public void saveToCurrentSprint(List<Place> currPlaces) throws SQLException {
        for (Place place : currPlaces) {
            place.sprint = currSprint;
            Database.places.create(place);
        }
    }

    public void saveItemsQR(List<Item> toGenQR, File file) {

    }
}
