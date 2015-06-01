import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Database;
import app.Config;

/**
 * Created by salterok on 04.05.2015.
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // todo add initialization
        Database.init(Config.getInstance().db.uri);

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
