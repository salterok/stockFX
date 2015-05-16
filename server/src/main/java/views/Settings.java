package views;

import controls.IProgressiveBasicRouting;
import javafx.scene.layout.GridPane;
import utils.ControlBuilder;

import java.util.function.Consumer;

/**
 * Created by salterok on 10.05.2015.
 */
public class Settings extends GridPane implements IProgressiveBasicRouting {
    public Settings() throws Exception {
        ControlBuilder.build(this);
    }

    @Override
    public void setNextCommand(Runnable command) {

    }

    @Override
    public void setPrevCommand(Runnable command) {

    }
}
