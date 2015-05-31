package utils;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by salterok on 30.05.2015.
 */
public class TaskUtil {
    public static void run(final Runnable action) {
        Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                action.run();
                return null;
            }
        };
        new Thread(task).start();
    }

    public static <T> Continuation<T> run(final Supplier<T> func) {
        ContinuationContext continuationContext = new ContinuationContext();
        Continuation<T> continuation = continuationContext.<T>addContinuation(false);
        Continuation ret = continuation.run(func);
        continuationContext.next(null);
        return ret;
    }

    public static class Continuation<T> {
        ContinuationContext context;
        T val;
        StatefulTask task;

        public Continuation() {}

        public Continuation<T> run(final Supplier<T> func) {
            Continuation<T> continuation = context.<T>addContinuation(true);

            StatefulTask task = new StatefulTask<T>() {
                @Override
                protected T call() throws Exception {
                    return func.get();
                }
            };
            task.setOnSucceeded(state -> context.next(task.getValue()));

            continuation.task = task;
            return continuation;
        }

        public Continuation thenUI(Consumer<T> func) {
            Continuation<T> continuation = context.<T>addContinuation(true);

            StatefulTask task = new StatefulTask<Void>() {
                @Override
                protected Void call() throws Exception {
                    func.accept((T)this.context);
                    return null;
                }
            };
            task.setOnSucceeded(state -> context.next(null));

            continuation.task = task;
            return continuation;
        }

        private void exec() {
            task.context = val;
            new Thread(task).start();
        }

        private T getVal() {
            return this.val;
        }
    }

    private static class ContinuationContext {

        private Queue<Continuation> queue = new ArrayBlockingQueue<Continuation>(100);

        private <T> Continuation<T> addContinuation(boolean addToQueue) {
            Continuation<T> continuation = new Continuation<>();
            continuation.context = this;
            if (addToQueue)
                queue.add(continuation);
            return continuation;
        }

        private void next(Object val) {
            Continuation continuation = queue.poll();
            if (continuation != null) {
                continuation.val = val;
                continuation.exec();
            }
        }

    }

    private static abstract class StatefulTask<T> extends Task<T> {
        Object context;
    }
}
