import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Database;
import model.SprintRepositoryImpl;
import model.dbo.Item;
import model.dbo.Place;
import model.dbo.RealItem;
import model.dbo.Sprint;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by salterok on 04.05.2015.
 */
public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {

        Database.init("jdbc:sqlite:database.db");

        views.Main root = new views.Main();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 475));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
