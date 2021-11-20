package io.github.mczeyrox.footballmanager.core.utility.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Event {

    private static Map<String, List<EventListener>> eventListeners = new HashMap<>();

    public static void AddListener(EventListener event) {
        String eventName = event.getClass().getName();
        if (!eventListeners.containsKey(eventName)) {
            eventListeners.put(eventName, new ArrayList<>());
        }
        eventListeners.get(eventName).add(event);
    }

    public static void EmitEvent(Event event) {

    }
}
