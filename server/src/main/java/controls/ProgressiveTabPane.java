package controls;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import utils.ControlBuilder;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by salterok on 04.05.2015.
 */
public class ProgressiveTabPane extends BorderPane {
    @FXML
    private Button helpBtn;
    @FXML
    private Button escapeBtn;
    @FXML
    private VBox progressiveBar;
    @FXML
    private AnchorPane content;

    private List<Map.Entry<String, Pane>> navBar = new ArrayList<>();


    public ProgressiveTabPane() throws Exception {
        ControlBuilder.build(this);
    }

    public void addProgressItem(String title, Pane pane) {
        navBar.add(new AbstractMap.SimpleImmutableEntry<>(title, pane));
    }

    public void setup() {
        drawProgressiveBar();
        setContent("start");
    }

    private void drawProgressiveBar() {
        List<HBox> items = navBar.stream().map(entry -> {
            HBox hBox = new HBox();
            Label label = new Label(entry.getKey());
            HBox.setHgrow(label, Priority.ALWAYS);
            hBox.paddingProperty().setValue(new Insets(2.0, 0.0, 2.0, 0.0));
            hBox.getChildren().add(label);
            hBox.setStyle("-fx-background-color: #2c2;");
            return hBox;
        }).collect(Collectors.toList());
        progressiveBar.getChildren().addAll(items);
    }

    private void setContent(String title) {
        Optional<Pane> pane = navBar.stream()
                .filter(entry -> entry.getKey().equals(title))
                .findFirst()
                .map(Map.Entry::getValue);
        content.getChildren().clear();
        if (pane.isPresent())
            content.getChildren().add(pane.get());
    }
}
