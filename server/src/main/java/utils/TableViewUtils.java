package utils;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import model.dbo.Place;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Created by salterok on 31.05.2015.
 */
public class TableViewUtils {
    private static final Logger logger = LogManager.getLogger(TableViewUtils.class);

    public static ObservableValue<String> readOnlyIndexedCellValueFactory(TableColumn.CellDataFeatures<List<String>, String> param) {
        return new SimpleStringProperty(param.getValue().get(
                param.getTableView().getColumns().indexOf(
                        param.getTableColumn()
                )
        ));
    }

    public static <T> void bindTo(TableView<T> table, Class<T> clazz) {
        ObservableList<TableColumn<T, ?>> columns = table.getColumns();
        Map<TableColumn<T, ?>, Integer> list = new HashMap<>();
        columns.clear();

        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotationsByType(TableViewBind.class);
            if (annotations.length == 1) {
                TableColumn<T, ?> column = new TableColumn<>(field.getName());
                column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
                list.put(column, ((TableViewBind) annotations[0]).priority());
            }
        }
        List<TableColumn<T, ?>> sorted = list.entrySet().stream()
                .sorted((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        columns.addAll(sorted);
    }

    public static <T> void setRowClickEvent(TableView<T> table, Consumer<T> callback) {
        table.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                try {
                    Node node = ((Node) event.getTarget()).getParent();
                    TableRow<T> row;
                    if (node instanceof TableRow) {
                        row = (TableRow) node;
                    } else {
                        // clicking on text part
                        row = (TableRow) node.getParent();
                    }
                    callback.accept(row.getItem());
                }
                catch (Exception ex) {
                    logger.error(ex);
                }
            }
        });
    }


    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public static @interface TableViewBind {
        int priority() default 0;


    }
}
