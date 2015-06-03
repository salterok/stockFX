package app;

import model.Database;
import model.dbo.Place;
import model.dbo.Sprint;
import views.Settings;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.List;

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
        return Database.places.getSprintPlaces(prevSprint.id);
    }
}
