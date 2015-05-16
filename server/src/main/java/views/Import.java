/**
 * Created by salterok on 10.05.2015.
 */

package views;

import controls.IProgressiveBasicRouting;
import javafx.scene.layout.GridPane;
import utils.ControlBuilder;

import java.util.function.Consumer;
public class Import extends GridPane implements IProgressiveBasicRouting {
    public Import() throws Exception {
        ControlBuilder.build(this);
    }

    @Override
    public void setNextCommand(Runnable command) {

    }

    @Override
    public void setPrevCommand(Runnable command) {

    }
}
