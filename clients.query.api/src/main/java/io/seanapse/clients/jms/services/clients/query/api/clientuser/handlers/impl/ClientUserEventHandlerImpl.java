package io.seanapse.clients.jms.services.clients.query.api.clientuser.handlers.impl;

import io.seanapse.clients.jms.services.clients.core.clientuser.events.ClientUserCreatedEvent;
import io.seanapse.clients.jms.services.clients.core.clientuser.events.ClientUserRemovedEvent;
import io.seanapse.clients.jms.services.clients.core.clientuser.events.ClientUserUpdatedEvent;
import io.seanapse.clients.jms.services.clients.query.api.clientuser.handlers.ClientUserEventHandler;
import io.seanapse.clients.jms.services.clients.query.api.clientuser.repositories.ClientUserRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("clients-group")
public class ClientUserEventHandlerImpl implements ClientUserEventHandler {
    private final ClientUserRepository clientUserRepository;

    @Autowired
    public ClientUserEventHandlerImpl(ClientUserRepository clientUserRepository) {
        this.clientUserRepository = clientUserRepository;
    }

    @Override
    @EventHandler
    public void on(ClientUserCreatedEvent event) {
        clientUserRepository.save(event.getClientUser());
    }

    @Override
    @EventHandler
    public void on(ClientUserUpdatedEvent event) {
        clientUserRepository.save(event.getClientUser());
    }

    @Override
    @EventHandler
    public void on(ClientUserRemovedEvent event) {
        clientUserRepository.deleteById(event.getId());
    }
}
