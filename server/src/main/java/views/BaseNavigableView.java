package views;

import constants.NavigationMethod;
import controls.IProgressiveBasicRouting;
import controls.IProgressiveCustomRouting;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import pojo.NavigationDescriptor;
import utils.ControlBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by salterok on 16.05.2015.
 */
public class BaseNavigableView extends BorderPane implements IProgressiveBasicRouting, IProgressiveCustomRouting {
    private Runnable nextCommand;
    private Runnable prevCommand;
    private Consumer<String> customCommand;

    public BaseNavigableView() throws Exception{
        ControlBuilder.build(this);
        init();
    }

    @Override
    public void setNextCommand(Runnable nextCommand) {
        this.nextCommand = nextCommand;
    }

    @Override
    public void setPrevCommand(Runnable prevCommand) {
        this.prevCommand = prevCommand;
    }

    @Override
    public void setCustomCommand(Consumer<String> customCommand) {
        this.customCommand = customCommand;
    }

    @FXML
    private void next(ActionEvent event) {
        nextCommand.run();
    }

    @FXML
    private void prev(ActionEvent event) {
        prevCommand.run();
    }

    @FXML
    private void custom(ActionEvent event) {
        Button btn = (Button)event.getSource();
        NavigationDescriptor desc = (NavigationDescriptor)btn.getUserData();
        customCommand.accept(desc.value);
    }

    private void init() {

    }

    public void setNavigationBar(NavigationDescriptor[] navs) {
        if (navs == null) {
            return;
        }
        HBox pane = new HBox();
        pane.alignmentProperty().setValue(Pos.BOTTOM_RIGHT);
        for (NavigationDescriptor nav : navs) {
            Button btn = new Button(nav.title);
            btn.setUserData(nav);
            EventHandler<ActionEvent> handler;
            switch (nav.method) {
                case PREV:
                    handler = this::prev;
                    break;
                case NEXT:
                    handler = this::next;
                    break;
                case CUSTOM:
                default:
                    handler = this::custom;
                    break;
            }
            btn.setDisable(!nav.isEnabled);
            btn.setOnAction(handler);
            pane.getChildren().add(btn);
        }
        this.setBottom(pane);
    }
}
