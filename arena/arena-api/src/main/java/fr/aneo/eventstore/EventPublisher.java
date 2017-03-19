package fr.aneo.eventstore;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by eeugene on 14/03/2017.
 */
public class EventPublisher {

    private Map<Class, List<Consumer<Event>>> listeners = new HashMap<>();

    public void addListener(Class<? extends Event> eventClass, Consumer<? extends Event> handler) {
        List<Consumer<Event>> handlers = listeners.get(eventClass);
        if (handlers == null) {
            handlers = new LinkedList<>();
            listeners.put(eventClass, handlers);
        }
        handlers.add((Consumer<Event>) handler);
    }

    public void publish(Event event) {
        List<Consumer<Event>> consumers = listeners.get(event.getClass());
        if (consumers != null) {
            consumers.forEach(c -> c.accept(event));
        }
    }
}
