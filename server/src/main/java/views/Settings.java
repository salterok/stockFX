package views;

import app.SettingsStage;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import model.Database;
import model.dbo.Place;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pojo.LocalConfig;
import utils.TableViewUtils;
import utils.TaskUtil;

import java.sql.SQLException;
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

    public Settings() throws Exception {}

    @Override
    protected void onLoad() throws Exception {
        TableViewUtils.bindTo(locationsTable, Place.class);
        TableViewUtils.bindTo(newLocationsTable, Place.class);
        TableViewUtils.setRowClickEvent(locationsTable, this::placesClicked);
        TableViewUtils.setRowClickEvent(newLocationsTable, this::newPlacesClicked);
    }

    @Override
    protected void onShown() {
        TaskUtil.run(() -> {
            LocalConfig.PlacesResult placesResult = new LocalConfig.PlacesResult();
            settingsStage.init();
            placesResult.prevPlaces = settingsStage.getPreviousPlaces();
            placesResult.currPlaces = settingsStage.getCurrentPlaces();
            return placesResult;
        }).thenUI(pr -> {
            locationsTable.setItems(FXCollections.observableList(pr.prevPlaces));
            newLocationsTable.setItems(FXCollections.observableList(pr.currPlaces));
        });
    }

    @FXML
    private void addOne(ActionEvent event) {
        Place place = locationsTable.getSelectionModel().getSelectedItem();
        placesClicked(place);
    }

    @FXML
    private void addAll(ActionEvent event) {
        List<Place> places = locationsTable.getItems().stream().collect(Collectors.toList());
        newLocationsTable.setItems(FXCollections.observableList(places));
    }

    @FXML
    private void resetAll(ActionEvent event) {
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
}
