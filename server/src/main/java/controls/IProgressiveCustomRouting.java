package controls;

import pojo.ProgressiveState;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by salterok on 16.05.2015.
 */
public interface IProgressiveCustomRouting {
    void setStateGetter(Supplier<ProgressiveState> stateGetter);
    void setCustomCommand(Consumer<String> command);
}
