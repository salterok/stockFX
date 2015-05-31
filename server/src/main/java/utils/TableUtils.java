package utils;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;

import java.util.List;
import java.util.Observable;

/**
 * Created by salterok on 31.05.2015.
 */
public class TableUtils {
    public static ObservableValue<String> readOnlyIndexedCellValueFactory(TableColumn.CellDataFeatures<List<String>, String> param) {
        return new SimpleStringProperty(param.getValue().get(
                param.getTableView().getColumns().indexOf(
                        param.getTableColumn()
                )
        ));
    }
}
