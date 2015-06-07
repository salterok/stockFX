package utils;

import app.Config;
import app.api.SendToClient;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import org.restlet.routing.Template;
import org.restlet.routing.TemplateRoute;

/**
 * Created by salterok on 07.06.2015.
 */
public class RestletUtil extends Application {

    private static Component component;

    @Override
    public Restlet createInboundRoot() {
        // Create a router Restlet that routes each call to a new instance of HelloWorldResource.
        Router router = new Router(getContext());
        // Defines only one route
        router.attach("/getSprintState", SendToClient.class);

        return router;
    }

    public static void startRest() throws Exception {
        if (component != null) {
            component.stop();
        }

        component = new Component();

        // Add a new HTTP server listening on port 8182.
        component.getServers().add(Protocol.HTTP, Config.getInstance().terminalSync.restPort);

        // Attach the sample application.
        component.getDefaultHost().attach(Config.getInstance().terminalSync.rootPath, new RestletUtil());

        // Start the component.
        component.start();

    }

    public static void stopRest() throws Exception {
        if (component != null) {
            component.stop();
        }
    }
}
