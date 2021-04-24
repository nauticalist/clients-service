package io.seanapse.clients.jms.services.clients.query.api.client.handlers;

import io.seanapse.clients.jms.services.clients.core.client.events.ClientCreatedEvent;
import io.seanapse.clients.jms.services.clients.core.client.events.ClientRemovedEvent;
import io.seanapse.clients.jms.services.clients.core.client.events.ClientUpdatedEvent;

public interface ClientEventHandler {
    void on(ClientCreatedEvent event);
    void on(ClientUpdatedEvent event);
    void on(ClientRemovedEvent event);
}
