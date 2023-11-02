package utils;

import java.io.Closeable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CloseablePooledExecutor implements Executor, Closeable {
    private final ExecutorService executorService = Executors.newFixedThreadPool(3);

    @Override
    public void execute(Runnable command) {
        executorService.execute(command);
    }

    @Override
    public void close() {
        executorService.shutdownNow();
    }
}
