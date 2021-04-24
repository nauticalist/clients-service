package io.seanapse.clients.jms.services.clients.cmd.api.client.commands;

import io.seanapse.clients.jms.services.clients.core.client.models.Client;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class UpdateClientCommand {
    @TargetAggregateIdentifier
    private String id;

    @Valid
    @NotNull(message = "no client details provided")
    private Client client;
}
