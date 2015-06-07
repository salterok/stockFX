package model.dbo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by salterok on 31.05.2015.
 */
@DatabaseTable(tableName = "Sprint")
public class Sprint {
    public static final String ID = "Sprint_Id";
    public static final String DATE_START = "Sprint_DateStart";
    public static final String DATE_END = "Sprint_DateEnd";
    public static final String OPERATOR = "Sprint_Operator";

    @DatabaseField(unique = true, generatedId = true, columnName = ID)
    public int id;
    @DatabaseField(canBeNull = true, columnName = DATE_START)
    public Date dateStart;
    @DatabaseField(canBeNull = true, columnName = DATE_END)
    public Date dateEnd;
    @DatabaseField(canBeNull = true, columnName = OPERATOR)
    public String operator;
    @JsonIgnore
    @ForeignCollectionField
    public ForeignCollection<Item> items;
}
