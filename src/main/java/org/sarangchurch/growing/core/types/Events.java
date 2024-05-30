package org.sarangchurch.growing.core.types;

import org.springframework.context.ApplicationEventPublisher;

public class Events {
    static ApplicationEventPublisher publisher;

    public static void setPublisher(ApplicationEventPublisher publisher) {
        if (publisher != null) {
            Events.publisher = publisher;
        }
    }

    public static void raise(Object event) {
        if (publisher != null) {
            publisher.publishEvent(event);
        }
    }

    private Events() {
    }
}
