package io.seanapse.clients.jms.services.clients.cmd.api.clientuser.aggregates;

import io.seanapse.clients.jms.services.clients.cmd.api.clientuser.commands.CreateClientUserCommand;
import io.seanapse.clients.jms.services.clients.cmd.api.clientuser.commands.RemoveClientUserCommand;
import io.seanapse.clients.jms.services.clients.cmd.api.clientuser.commands.UpdateClientUserCommand;
import io.seanapse.clients.jms.services.clients.core.client.events.ClientRemovedEvent;
import io.seanapse.clients.jms.services.clients.core.clientuser.events.ClientUserCreatedEvent;
import io.seanapse.clients.jms.services.clients.core.clientuser.events.ClientUserRemovedEvent;
import io.seanapse.clients.jms.services.clients.core.clientuser.events.ClientUserUpdatedEvent;
import io.seanapse.clients.jms.services.clients.core.clientuser.models.ClientUser;
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
public class ClientUserAggregate {
    @AggregateIdentifier
    private String id;

    private ClientUser clientUser;

    @CommandHandler
    public ClientUserAggregate(CreateClientUserCommand command) {
        var newUser = command.getClientUser();
        newUser.setId(command.getId());
        newUser.setCreatedAt(new Date());

        var event = ClientUserCreatedEvent.builder()
                .id(command.getId())
                .clientUser(newUser)
                .build();
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(ClientUserCreatedEvent event) {
        this.id = event.getId();
        this.clientUser = event.getClientUser();
    }

    @CommandHandler
    public void handle(UpdateClientUserCommand command) {
        var updatedUser = command.getClientUser();
        updatedUser.setId(command.getId());
        updatedUser.setModifiedAt(new Date());
        updatedUser.setCreatedBy(this.clientUser.getCreatedBy());
        updatedUser.setCreatedAt(this.clientUser.getCreatedAt());

        var event = ClientUserUpdatedEvent.builder()
                .id(UUID.randomUUID().toString())
                .clientUser(updatedUser)
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(ClientUserUpdatedEvent event) {
        this.clientUser = event.getClientUser();
    }

    @CommandHandler
    public void handle(RemoveClientUserCommand command) {
        var event = new ClientUserRemovedEvent();
        event.setId(command.getId());

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(ClientUserRemovedEvent event) {
        AggregateLifecycle.markDeleted();
    }
}
