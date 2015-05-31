package model.dbo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

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
    @DatabaseField(canBeNull = false, columnName = ITEM_ID, foreign = true, foreignColumnName = Item.ID, foreignAutoRefresh = true)
    public Item item;
    @DatabaseField(canBeNull = false, columnName = PLACE_ID, foreign = true, foreignColumnName = Place.ID, foreignAutoRefresh = true)
    public Place place;
    @DatabaseField(canBeNull = false, columnName = STATE)
    public State state;

    public enum State {
        NEW,
        OLD
    }
}
