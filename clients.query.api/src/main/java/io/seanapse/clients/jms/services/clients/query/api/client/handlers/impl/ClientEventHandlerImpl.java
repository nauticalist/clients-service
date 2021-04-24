package io.seanapse.clients.jms.services.clients.query.api.client.handlers.impl;

import io.seanapse.clients.jms.services.clients.core.client.events.ClientCreatedEvent;
import io.seanapse.clients.jms.services.clients.core.client.events.ClientRemovedEvent;
import io.seanapse.clients.jms.services.clients.core.client.events.ClientUpdatedEvent;
import io.seanapse.clients.jms.services.clients.query.api.client.handlers.ClientEventHandler;
import io.seanapse.clients.jms.services.clients.query.api.client.repositories.ClientRepository;
import io.seanapse.clients.jms.services.clients.query.api.clientuser.repositories.ClientUserRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("clients-group")
public class ClientEventHandlerImpl implements ClientEventHandler {
    private final ClientRepository clientRepository;
    private final ClientUserRepository clientUserRepository;

    @Autowired
    public ClientEventHandlerImpl(ClientRepository clientRepository, ClientUserRepository clientUserRepository) {
        this.clientRepository = clientRepository;
        this.clientUserRepository = clientUserRepository;
    }

    @Override
    @EventHandler
    public void on(ClientCreatedEvent event) {
        clientRepository.save(event.getClient());
    }

    @Override
    @EventHandler
    public void on(ClientUpdatedEvent event) {
        clientRepository.save(event.getClient());
    }

    @Override
    @EventHandler
    public void on(ClientRemovedEvent event) {
        clientRepository.deleteById(event.getId());
        clientUserRepository.deleteClientUsersByClientId(event.getId());
    }
}
