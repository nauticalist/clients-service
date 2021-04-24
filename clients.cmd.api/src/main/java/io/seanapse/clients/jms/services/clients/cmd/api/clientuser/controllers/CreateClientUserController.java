package io.seanapse.clients.jms.services.clients.cmd.api.clientuser.controllers;

import io.seanapse.clients.jms.services.clients.cmd.api.client.commands.CreateClientCommand;
import io.seanapse.clients.jms.services.clients.cmd.api.clientuser.commands.CreateClientUserCommand;
import io.seanapse.clients.jms.services.clients.cmd.api.clientuser.dto.CreateClientUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/clients")
public class CreateClientUserController {
    private final CommandGateway commandGateway;

    @Autowired
    public CreateClientUserController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping(path = "/{clientId}/users")
    @PreAuthorize("hasAnyRole('ROLE_CLIENTS_MANAGER')")
    public ResponseEntity<CreateClientUserResponse> createClientUser(
            @PathVariable(value = "clientId") String clientId,
            @Valid @RequestBody CreateClientUserCommand command,
            @AuthenticationPrincipal Jwt principal) {
        String userId = (String) principal.getClaims().get("sub");
        var id = UUID.randomUUID().toString();

        try {
            command.setId(id);
            command.getClientUser().setClientId(clientId);
            command.getClientUser().setCreatedBy(userId);

            commandGateway.sendAndWait(command);

            return new ResponseEntity<>(new CreateClientUserResponse("Successfully created new user for client with id - " + id, id), HttpStatus.CREATED);
        } catch (Exception e) {
            var errorMessage = "Error while processing request to create a new user for client with id - " + id;
            log.error(e.toString());

            return new ResponseEntity<>(new CreateClientUserResponse(errorMessage, id), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
