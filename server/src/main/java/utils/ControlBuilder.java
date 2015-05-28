package utils;

import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by salterok on 09.05.2015.
 */
public class ControlBuilder {
    public static Locale locale = Locale.forLanguageTag("ru");

    private static ResourceBundle _build(Object instance, String path) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(instance.getClass().getResource(path));
        ResourceBundle bundle = ResourceBundle.getBundle("bundles." + instance.getClass().getSimpleName(),
                locale,
                new UTF8Control());
        fxmlLoader.setResources(bundle);
        fxmlLoader.setRoot(instance);
        fxmlLoader.setController(instance);
        fxmlLoader.load();
        return bundle;
    }

    public static ResourceBundle bindView(Object instance) throws IOException {
       return  _build(instance, instance.getClass().getSimpleName() + ".fxml");
    }
}
