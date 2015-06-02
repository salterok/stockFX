package model.dbo;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by salterok on 01.06.2015.
 */
@DatabaseTable(tableName = "Place")
public class Place {
    public static final String ID = "Place_Id";
    public static final String TITLE = "Place_Title";

    @DatabaseField(generatedId = true, columnName = ID)
    public int id;
    @DatabaseField(canBeNull = false, columnName = TITLE)
    public String title;
    @ForeignCollectionField
    public ForeignCollection<RealItem> realItems;
}
