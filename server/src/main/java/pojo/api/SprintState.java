package pojo.api;

import model.dbo.Item;
import model.dbo.Place;
import model.dbo.RealItem;

import java.util.List;

/**
 * Created by salterok on 07.06.2015.
 */
public class SprintState {
    public List<Item> items;
    public List<RealItem> realItems;
    public List<Place> places;

    public SprintState(List<Item> items, List<RealItem> realItems, List<Place> places) {
        this.items = items;
        this.realItems = realItems;
        this.places = places;
    }
}
