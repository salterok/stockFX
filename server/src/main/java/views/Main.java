package views;

import com.fasterxml.jackson.databind.ObjectMapper;
import controls.ProgressiveTabPane;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import pojo.ProgressiveViewItem;
import utils.ControlBuilder;

import java.io.InputStream;

/**
 * Created by salterok on 04.05.2015.
 */
public class Main extends BorderPane {

    @FXML
    private ProgressiveTabPane progressivePane;

    public Main() throws Exception {
        ControlBuilder.bindView(this);
        init();
    }

    private void init() throws Exception {
        prepareViews();
        progressivePane.setup();
    }

    private void prepareViews() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream stream = getClass().getResourceAsStream("/Views.json");
        ProgressiveViewItem[] items = mapper.readValue(stream, ProgressiveViewItem[].class);

        for (ProgressiveViewItem item : items) {
            item.instance = (BaseNavigableView)Class.forName(item.ref).newInstance();
            progressivePane.addProgressItem(item);
        }
    }



}
