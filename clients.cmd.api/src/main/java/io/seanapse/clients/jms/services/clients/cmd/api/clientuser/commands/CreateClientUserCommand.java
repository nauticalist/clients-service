package io.seanapse.clients.jms.services.clients.cmd.api.clientuser.commands;

import io.seanapse.clients.jms.services.clients.core.clientuser.models.ClientUser;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class CreateClientUserCommand {
    @TargetAggregateIdentifier
    private String id;

    @Valid
    @NotNull(message = "no client user details provided")
    private ClientUser clientUser;
}
