package io.seanapse.clients.jms.services.clients.cmd.api.client.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class RemoveClientCommand {
    @TargetAggregateIdentifier
    private String id;
}
