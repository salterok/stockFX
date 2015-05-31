package model.dbo;

import com.j256.ormlite.dao.BaseForeignCollection;
import com.j256.ormlite.dao.EagerForeignCollection;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;

/**
 * Created by salterok on 31.05.2015.
 */
@DatabaseTable(tableName = "Item")
public class Item {
    public static final String ID = "Item_Id";
    public static final String SPRINT_ID = "Item_SprintId";
    public static final String NAME = "Item_Name";
    public static final String PRICE = "Item_Price";

    @DatabaseField(generatedId = true, columnName = ID)
    public int id;
    @DatabaseField(canBeNull = false, columnName = SPRINT_ID, foreign = true, foreignColumnName = Sprint.ID, foreignAutoRefresh = true)
    public Sprint sprint;
    @DatabaseField(columnName = NAME, canBeNull = false)
    public String name;
    @DatabaseField(columnName = PRICE)
    public float price;
    @ForeignCollectionField
    public ForeignCollection<RealItem> realItems;
}
