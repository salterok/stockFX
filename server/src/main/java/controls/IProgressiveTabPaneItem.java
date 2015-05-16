package controls;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

/**
 * Created by salterok on 16.05.2015.
 */
public interface IProgressiveTabPaneItem {
    void setNextCommand(Runnable command);
    void setPrevCommand(Runnable command);
    void setCustomCommand(Consumer<String> command);
}
