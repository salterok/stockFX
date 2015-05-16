package controls;

import java.util.function.Consumer;

/**
 * Created by salterok on 16.05.2015.
 */
public interface IProgressiveCustomRouting {
    void setCustomCommand(Consumer<String> command);
}
