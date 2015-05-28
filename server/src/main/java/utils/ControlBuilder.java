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

    private static void _build(Object instance, String path) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(instance.getClass().getResource(path));
        ResourceBundle bundle = ResourceBundle.getBundle("bundles." + instance.getClass().getSimpleName(), locale);
        fxmlLoader.setResources(bundle);
        fxmlLoader.setRoot(instance);
        fxmlLoader.setController(instance);
        fxmlLoader.load();
    }

    public static void build(Object instance) throws IOException {
        _build(instance, instance.getClass().getSimpleName() + ".fxml");
    }

    public static void build(Object instance, String viewPath) throws IOException {
        _build(instance, viewPath);
    }
}
