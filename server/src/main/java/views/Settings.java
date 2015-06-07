package views;

import app.SettingsStage;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import model.dbo.Item;
import model.dbo.Place;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pojo.LocalConfig;
import utils.TableViewUtils;
import utils.TaskUtil;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by salterok on 10.05.2015.
 */
public class Settings extends BaseNavigableView {
    private static final Logger logger = LogManager.getLogger(Settings.class);
    private SettingsStage settingsStage = new SettingsStage();
    @FXML
    private TableView<Place> locationsTable;
    @FXML
    private TableView<Place> newLocationsTable;
    @FXML
    private TableView<Item> itemsTable;
    @FXML
    private TableView<Item> qrGenItemsTable;

    public Settings() throws Exception {}

    @Override
    protected void onLoad() throws Exception {
        TableViewUtils.bindTo(locationsTable, Place.class);
        TableViewUtils.bindTo(newLocationsTable, Place.class);
        TableViewUtils.bindTo(itemsTable, Item.class);
        TableViewUtils.bindTo(qrGenItemsTable, Item.class);
        TableViewUtils.setRowClickEvent(locationsTable, this::placesClicked);
        TableViewUtils.setRowClickEvent(newLocationsTable, this::newPlacesClicked);
        TableViewUtils.setRowClickEvent(itemsTable, this::itemsClicked);
        TableViewUtils.setRowClickEvent(qrGenItemsTable, this::newItemsClicked);
    }

    @Override
    protected void onShown() {
        TaskUtil.run(() -> {
            LocalConfig.SettingStageResult settingStageResult = new LocalConfig.SettingStageResult();
            settingsStage.init();
            settingStageResult.prevPlaces = settingsStage.getPreviousPlaces();
            settingStageResult.currPlaces = settingsStage.getCurrentPlaces();
            settingsStage.getItemsToGenerateQR(settingStageResult);
            return settingStageResult;
        }).thenUI(pr -> {
            locationsTable.setItems(FXCollections.observableList(pr.prevPlaces));
            newLocationsTable.setItems(FXCollections.observableList(pr.currPlaces));
            itemsTable.setItems(FXCollections.observableList(pr.allItems));
            qrGenItemsTable.setItems(FXCollections.observableList(pr.toGenQR));
        });
    }

    @Override
    protected void next(ActionEvent event) {
        Button btn = (Button) event.getSource();
        btn.setDisable(true);
        btn.setText(getString("save_config"));
        Cursor cursor = this.getCursor();
        this.setCursor(Cursor.WAIT);
        //this.setDisable(true);

        LocalConfig.SettingStageResult props = getResult();
        if (props.toGenQR.size() > 0) {
            FileChooser chooser = new FileChooser();
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF-file", "*.pdf"));
            props.saveFile = chooser.showSaveDialog(null);
        }

        TaskUtil.run(this::saveConfig, props).thenUI(r -> {
            btn.setDisable(false);
            btn.setText(getString("make_phone_sync"));
            this.setCursor(cursor);
            nextCommand.run();
        });
    }

    private LocalConfig.SettingStageResult getResult() {
        LocalConfig.SettingStageResult result = new LocalConfig.SettingStageResult();
        result.currPlaces = newLocationsTable.getItems();
        result.toGenQR = qrGenItemsTable.getItems();
        return result;
    }

    private Void saveConfig(LocalConfig.SettingStageResult props) {
        try {
            settingsStage.saveToCurrentSprint(props.currPlaces);
            settingsStage.saveItemsQR(props.toGenQR, props.saveFile);
        }
        catch (Exception ex) {
            logger.error(ex);
        }
        return null;
    }

    // region places
    @FXML
    private void addOnePlace(ActionEvent event) {
        Place place = locationsTable.getSelectionModel().getSelectedItem();
        placesClicked(place);
    }

    @FXML
    private void addAllPlaces(ActionEvent event) {
        List<Place> places = locationsTable.getItems().stream().collect(Collectors.toList());
        newLocationsTable.setItems(FXCollections.observableList(places));
    }

    @FXML
    private void resetAllPlaces(ActionEvent event) {
        newLocationsTable.getItems().clear();
    }

    private void placesClicked(Place place) {
        if (place != null && !newLocationsTable.getItems().contains(place)) {
            newLocationsTable.getItems().add(place);
        }
    }

    private void newPlacesClicked(Place place) {
        newLocationsTable.getItems().remove(place);
    }
    // endregion places

    // region qr items
    @FXML
    private void addOneItem(ActionEvent event) {
        Item item = itemsTable.getSelectionModel().getSelectedItem();
        itemsClicked(item);
    }

    @FXML
    private void addAllItems(ActionEvent event) {
        List<Item> items = itemsTable.getItems().stream().collect(Collectors.toList());
        qrGenItemsTable.setItems(FXCollections.observableList(items));
    }

    @FXML
    private void resetAllItems(ActionEvent event) {
        qrGenItemsTable.getItems().clear();
    }

    private void itemsClicked(Item item) {
        if (item != null && !qrGenItemsTable.getItems().contains(item)) {
            qrGenItemsTable.getItems().add(item);
        }
    }

    private void newItemsClicked(Item item) {
        qrGenItemsTable.getItems().remove(item);
    }
    // endregion qr items

}
