package event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

public class EventManager implements EventFirerer, EventListener {
    private final Map<EventKey, List<EventHandler>> handlers;
    private final Executor executor;

    public EventManager(Executor executor) {
        this.executor = executor;

        this.handlers = new HashMap<>();
    }

    @Override
    public void listen(EventHandler listener, EventKey... eventKeys) {
        for (var key : eventKeys) {
            List<EventHandler> handlersForEvent;

            if (handlers.containsKey(key)) {
                handlersForEvent = handlers.get(key);
            } else {
                handlersForEvent = new ArrayList<>();
            }

            handlersForEvent.add(listener);

            handlers.put(key, handlersForEvent);
        }
    }

    @Override
    public void fire(EventKey eventKey) {
        executor.execute(() -> {
            synchronized (handlers) {
                if (handlers.containsKey(eventKey)) {
                    handlers.get(eventKey).forEach(EventHandler::handle);
                }
            }
        });
    }
}
