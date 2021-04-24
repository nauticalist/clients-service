package io.seanapse.clients.jms.services.clients.query.api.clientuser.handlers;

import io.seanapse.clients.jms.services.clients.core.clientuser.events.ClientUserCreatedEvent;
import io.seanapse.clients.jms.services.clients.core.clientuser.events.ClientUserRemovedEvent;
import io.seanapse.clients.jms.services.clients.core.clientuser.events.ClientUserUpdatedEvent;

public interface ClientUserEventHandler {
    void on(ClientUserCreatedEvent event);
    void on(ClientUserUpdatedEvent event);
    void on(ClientUserRemovedEvent event);
}
