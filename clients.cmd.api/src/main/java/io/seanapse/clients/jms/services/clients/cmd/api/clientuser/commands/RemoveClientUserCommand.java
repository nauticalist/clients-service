package io.seanapse.clients.jms.services.clients.cmd.api.clientuser.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class RemoveClientUserCommand {
    @TargetAggregateIdentifier
    private String id;
}
