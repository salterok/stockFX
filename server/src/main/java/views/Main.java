package views;

import controls.ProgressiveTabPane;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import pojo.ProgressiveViewItem;
import utils.ControlBuilder;

/**
 * Created by salterok on 04.05.2015.
 */
public class Main extends BorderPane {

    @FXML
    private ProgressiveTabPane progressivePane;

    public Main() throws Exception {
        ControlBuilder.build(this);
        init();
    }

    private void init() throws Exception {
        prepareViews();
        progressivePane.setup();
    }

    private void prepareViews() throws Exception {
        ProgressiveViewItem startView = new ProgressiveViewItem();
        startView.id = "start";
        startView.title = "start";
        startView.instance = new Start();
        ProgressiveViewItem importView = new ProgressiveViewItem();
        importView.id = "import";
        importView.title = "import";
        importView.instance = new Import();
        ProgressiveViewItem settingsView = new ProgressiveViewItem();
        settingsView.id = "settings";
        settingsView.title = "settings";
        settingsView.instance = new Settings();

        progressivePane.addProgressItem(startView);
        progressivePane.addProgressItem(importView);
        progressivePane.addProgressItem(settingsView);
    }



}
