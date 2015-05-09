package views;

import controls.ProgressiveTabPane;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import utils.ControlBuilder;

/**
 * Created by salterok on 04.05.2015.
 */
public class Main extends GridPane {

    @FXML
    private ProgressiveTabPane progressivePane;

    public Main() throws Exception {
        ControlBuilder.build(this);
        init();
    }

    private void init() throws Exception {
        progressivePane.addProgressItem("start", new Start());
        progressivePane.setup();
    }



}
