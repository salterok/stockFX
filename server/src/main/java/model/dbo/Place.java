package model.dbo;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import javafx.beans.property.*;
import utils.TableViewUtils.TableViewBind;

/**
 * Created by salterok on 01.06.2015.
 */
@DatabaseTable(tableName = "Place")
public class Place {
    public static final String ID = "Place_Id";
    public static final String SPRINT_ID = "Place_SprintId";
    public static final String TITLE = "Place_Title";

    @TableViewBind(priority = 1)
    @DatabaseField(generatedId = true, columnName = ID)
    public int id;
    @DatabaseField(canBeNull = false, columnName = SPRINT_ID, foreign = true, foreignColumnName = Sprint.ID, foreignAutoRefresh = true)
    public Sprint sprint;
    @TableViewBind(priority = 2)
    @DatabaseField(canBeNull = false, columnName = TITLE)
    public String title;
    @ForeignCollectionField
    public ForeignCollection<RealItem> realItems;

    public IntegerProperty idProperty() {
        return new SimpleIntegerProperty(id);
    }

    public StringProperty titleProperty() {
        return new SimpleStringProperty(title);
    }

}
