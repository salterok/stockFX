/**
 * Created by salterok on 10.05.2015.
 */

package views;

import com.sun.javaws.progress.Progress;
import controls.IProgressiveBasicRouting;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import utils.CSVReader;
import utils.ControlBuilder;
import utils.TaskUtil;

import java.beans.EventHandler;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Import extends BaseNavigableView {
    @FXML
    private TableView importPreviewTable;
    @FXML
    private StackPane previewStack;

    private ProgressIndicator previewProgressIndicator = null;

    public Import() throws Exception {}

    @FXML
    private void selectImportFile(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Standard CSV", "*.csv"));
        chooser.setTitle(getString("file-chooser-title"));
        File file = chooser.showOpenDialog(null);
        if (file != null) {
            stateSupplier.get().importState.selectedForImport = file;
        }
    }

    @FXML
    private void previewSelectionChanged(Event event) {
        if (event.getSource() instanceof Tab) {
            Tab tab = (Tab)event.getSource();
            if (tab.isSelected()) {
                TaskUtil.run(this::previewImport).thenUI(this::updatePreviewTable);
            }
        }
    }

    private void toggleProgressIndicator() {
        if (previewProgressIndicator == null) {
            previewProgressIndicator = new ProgressIndicator();
            previewStack.getChildren().add(previewProgressIndicator);
        }
        else {
            previewStack.getChildren().remove(previewProgressIndicator);
            previewProgressIndicator = null;
        }
    }

    private PreviewResult previewImport() {
        Platform.runLater(this::toggleProgressIndicator);
        PreviewResult result = new PreviewResult();
        List<List<String>> items = new ArrayList<>();
        try {
            Thread.sleep(200);

            CSVFormat format = CSVFormat.newFormat(';');
            List<CSVRecord> records = CSVReader.readFromFile(stateSupplier.get().importState.selectedForImport, format, 100);
            OptionalInt max = records.stream().mapToInt(r -> r.size()).max();
            result.columns = max.getAsInt();
            for (CSVRecord rec : records) {
                List<String> item = new ArrayList<>();
                int size = rec.size();
                for (int i = 0; i < max.getAsInt(); i++) {
                    if (i < size) {
                        item.add(rec.get(i));
                    }
                    else {
                        item.add("");
                    }
                }
                items.add(item);
            }
            result.items = items;
        } catch (Exception e) {
            e.printStackTrace();
        }


        Platform.runLater(this::toggleProgressIndicator);
        return result;
    }

    private void updatePreviewTable(PreviewResult result) {


        Platform.runLater(() -> {
            addCustomColumns(importPreviewTable, result.columns, new String[] {"daa", "---"});
            importPreviewTable.setItems(FXCollections.observableList(result.items));
        });
    }

    private void addCustomColumns(TableView table, int count, String[] options) {
        ObservableList columns = table.getColumns();
        columns.clear();
        columns.addAll(Collections.nCopies(count, 0).stream().map(i -> createChooserColumn(options)).collect(Collectors.toList()));
    }

    private TableColumn createChooserColumn(String[] options) {
        TableColumn col = new TableColumn();
        ChoiceBox choiceBox = new ChoiceBox();
        choiceBox.setItems(FXCollections.observableArrayList(options));
        col.setGraphic(choiceBox);
        return col;
    }

    private class PreviewResult {
        public int columns;
        public List<List<String>> items;
    }
}
