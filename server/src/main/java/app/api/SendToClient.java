package app.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import model.Database;
import model.dbo.Item;
import model.dbo.Place;
import model.dbo.RealItem;
import model.dbo.Sprint;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import pojo.api.SprintState;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by salterok on 07.06.2015.
 */
public class SendToClient extends ServerResource {

    @Get("json")
    public String currentSprintData() {
        Sprint sprint = Database.sprints.getCurrent();
        List<Item> items = Database.items.getSprintItems(sprint.id);
        List<RealItem> realItems = items.stream()
                .map(i -> i.realItems.toArray(new RealItem[]{}))
                .flatMap(f -> Arrays.stream(f))
                .collect(Collectors.toList());
        List<Place> places = Database.places.getSprintPlaces(sprint.id);

        SprintState sprintState = new SprintState(items, realItems, places);
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(sprintState);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "ERROR " + e.getMessage();
        }
    }
}
