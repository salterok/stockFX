package views;

import controls.IProgressiveBasicRouting;
import controls.IProgressiveCustomRouting;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import utils.ControlBuilder;

import java.util.function.Consumer;

/**
 * Created by salterok on 10.05.2015.
 */
public class Start extends BorderPane implements IProgressiveBasicRouting, IProgressiveCustomRouting {
    private Runnable nextCommand;
    private Runnable prevCommand;
    private Consumer<String> customCommand;

    @Override
    public void setCustomCommand(Consumer<String> customCommand) {
        this.customCommand = customCommand;
    }

    @Override
    public void setNextCommand(Runnable nextCommand) {
        this.nextCommand = nextCommand;
    }

    @Override
    public void setPrevCommand(Runnable prevCommand) {
        this.prevCommand = prevCommand;
    }

    public Start() throws Exception {
        ControlBuilder.build(this);
    }

    @FXML
    private void next(ActionEvent event) {
        nextCommand.run();
    }

    @FXML
    private void prev(ActionEvent event) {
        prevCommand.run();
    }



}
