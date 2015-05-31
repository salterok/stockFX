import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by salterok on 04.05.2015.
 */
public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        views.Main root = new views.Main();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 475));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
