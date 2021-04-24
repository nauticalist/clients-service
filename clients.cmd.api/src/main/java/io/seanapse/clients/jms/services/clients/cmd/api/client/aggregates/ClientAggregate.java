package io.seanapse.clients.jms.services.clients.cmd.api.client.aggregates;

import io.seanapse.clients.jms.services.clients.cmd.api.client.commands.CreateClientCommand;
import io.seanapse.clients.jms.services.clients.cmd.api.client.commands.RemoveClientCommand;
import io.seanapse.clients.jms.services.clients.cmd.api.client.commands.UpdateClientCommand;
import io.seanapse.clients.jms.services.clients.core.client.events.ClientCreatedEvent;
import io.seanapse.clients.jms.services.clients.core.client.events.ClientRemovedEvent;
import io.seanapse.clients.jms.services.clients.core.client.events.ClientUpdatedEvent;
import io.seanapse.clients.jms.services.clients.core.client.models.Client;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Date;
import java.util.UUID;

@Aggregate
@NoArgsConstructor
public class ClientAggregate {
    @AggregateIdentifier
    private String id;

    private Client client;

    @CommandHandler
    public ClientAggregate(CreateClientCommand command) {
        var newClient = command.getClient();
        newClient.setId(command.getId());
        newClient.setCreatedAt(new Date());

        var event = ClientCreatedEvent.builder()
                .id(command.getId())
                .client(newClient)
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(ClientCreatedEvent event) {
        this.id = event.getId();
        this.client = event.getClient();
    }

    @CommandHandler
    public void handle(UpdateClientCommand command) {
        var updatedClient = command.getClient();
        updatedClient.setId(command.getId());
        updatedClient.setModifiedAt(new Date());
        updatedClient.setCreatedAt(this.client.getCreatedAt());
        updatedClient.setCreatedBy(this.client.getCreatedBy());

        var event = ClientUpdatedEvent.builder()
                .id(UUID.randomUUID().toString())
                .client(updatedClient)
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(ClientUpdatedEvent event) {
        this.client = event.getClient();
    }

    @CommandHandler
    public void handle(RemoveClientCommand command) {
        var event = new ClientRemovedEvent();
        event.setId(command.getId());

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(ClientRemovedEvent event) {
        AggregateLifecycle.markDeleted();
    }
}
