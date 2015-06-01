import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Database;
import model.SprintRepositoryImpl;
import model.dbo.Item;
import model.dbo.Place;
import model.dbo.RealItem;
import model.dbo.Sprint;

import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by salterok on 04.05.2015.
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // todo add initialization
        Database.init("jdbc:sqlite:data/database.db");

        views.Main root = new views.Main();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 475));
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> Database.dispose());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
