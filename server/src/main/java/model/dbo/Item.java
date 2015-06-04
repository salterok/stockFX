package model.dbo;

import com.j256.ormlite.dao.BaseForeignCollection;
import com.j256.ormlite.dao.EagerForeignCollection;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;
import javafx.beans.property.*;
import utils.TableViewUtils;
import utils.TableViewUtils.TableViewBind;

import java.sql.SQLException;

/**
 * Created by salterok on 31.05.2015.
 */
@DatabaseTable(tableName = "Item")
public class Item {
    public static final String ID = "Item_Id";
    public static final String INV_NUMBER = "Item_InvNumber";
    public static final String SPRINT_ID = "Item_SprintId";
    public static final String NAME = "Item_Name";
    public static final String BILL = "Item_Bill";
    public static final String PRICE = "Item_Price";

    @DatabaseField(generatedId = true, columnName = ID)
    public int id;
    @TableViewBind(priority = 1)
    @DatabaseField(canBeNull = false, columnName = INV_NUMBER)
    public long invNumber;
    @DatabaseField(canBeNull = false, columnName = SPRINT_ID, foreign = true, foreignColumnName = Sprint.ID, foreignAutoRefresh = true)
    public Sprint sprint;
    @TableViewBind(priority = 2)
    @DatabaseField(columnName = NAME, canBeNull = false)
    public String name;
    @TableViewBind(priority = 3)
    @DatabaseField(columnName = BILL, canBeNull = true)
    public String bill;
    // not in db
    @TableViewBind(priority = 5)
    public int count = -1;
    @TableViewBind(priority = 4)
    @DatabaseField(columnName = PRICE)
    public float price;
    @ForeignCollectionField
    public ForeignCollection<RealItem> realItems;

    public LongProperty invNumberProperty() {
        return new SimpleLongProperty(invNumber);
    }

    public StringProperty billProperty() {
        return new SimpleStringProperty(bill);
    }

    public IntegerProperty countProperty() {
        count = realItems.size();
        return new SimpleIntegerProperty(count);
    }

    public FloatProperty priceProperty() {
        return new SimpleFloatProperty(price);
    }
}
