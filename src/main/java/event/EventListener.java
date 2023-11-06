package event;

public interface EventListener {
    void listen(EventHandler listener, EventKey... eventKeys);
}
