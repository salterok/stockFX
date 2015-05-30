/**
 * Created by salterok on 10.05.2015.
 */

package views;

import controls.IProgressiveBasicRouting;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import utils.ControlBuilder;

import java.io.File;
import java.util.function.Consumer;
public class Import extends BaseNavigableView {
    public Import() throws Exception {

    }

    @FXML
    private void selectImportFile(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Standard CSV", ".csv"));
        chooser.setTitle(getString("file-chooser-title"));
        File file = chooser.showOpenDialog(null);
        if (file != null) {
            
        }
    }
}
