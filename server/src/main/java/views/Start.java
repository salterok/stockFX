package views;

import controls.IProgressiveTabPaneItem;
import javafx.scene.layout.GridPane;
import utils.ControlBuilder;

import java.util.function.Consumer;

/**
 * Created by salterok on 10.05.2015.
 */
public class Start extends GridPane implements IProgressiveTabPaneItem {
    public Start() throws Exception {
        ControlBuilder.build(this);
    }

    @Override
    public void setNextCommand(Runnable command) {

    }

    @Override
    public void setPrevCommand(Runnable command) {

    }

    @Override
    public void setCustomCommand(Consumer<String> command) {

    }
}
