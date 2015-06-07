package model.dbo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import sun.management.counter.perf.PerfLongArrayCounter;

/**
 * Created by salterok on 01.06.2015.
 */
@DatabaseTable(tableName = "RealItem")
public class RealItem {
    public static final String ID = "RealItem_Id";
    public static final String ITEM_ID = "RealItem_ItemId";
    public static final String PLACE_ID = "RealItem_PlaceId";
    public static final String STATE = "RealItem_State";

    @DatabaseField(generatedId = true, columnName = ID)
    public int id;
    @JsonIgnore
    @DatabaseField(canBeNull = false, columnName = ITEM_ID, foreign = true, foreignColumnName = Item.ID, foreignAutoRefresh = true)
    public Item item;
    @DatabaseField(canBeNull = true, columnName = ITEM_ID)
    public int itemId;
    @JsonIgnore
    @DatabaseField(canBeNull = true, columnName = PLACE_ID, foreign = true, foreignColumnName = Place.ID, foreignAutoRefresh = true)
    public Place place;
    @DatabaseField(canBeNull = true, columnName = PLACE_ID)
    public int placeId;
    @DatabaseField(canBeNull = true, columnName = STATE)
    public State state;

    public enum State {
        NEW,
        OLD
    }
}
