package io.seanapse.clients.jms.services.clients.cmd.api.client.controllers;

import io.seanapse.clients.jms.services.clients.cmd.api.client.commands.CreateClientCommand;
import io.seanapse.clients.jms.services.clients.cmd.api.client.dto.CreateClientResponse;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/clients")
public class CreateClientController {
    private final CommandGateway commandGateway;

    @Autowired
    public CreateClientController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_CLIENTS_MANAGER')")
    public ResponseEntity<CreateClientResponse> createClient(@Valid @RequestBody CreateClientCommand command,
                                                             @AuthenticationPrincipal Jwt principal) {
        String userId = (String) principal.getClaims().get("sub");
        var id = UUID.randomUUID().toString();

        try {
            command.setId(id);
            command.getClient().setCreatedBy(userId);
            commandGateway.sendAndWait(command);

            return new ResponseEntity<>(new CreateClientResponse("Successfully created new client", id), HttpStatus.CREATED);
        } catch (Exception e) {
            var errorMessage = "Error while processing request to create a new client for id - " + id;
            log.error(e.toString());

            return new ResponseEntity<>(new CreateClientResponse(errorMessage, id), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
