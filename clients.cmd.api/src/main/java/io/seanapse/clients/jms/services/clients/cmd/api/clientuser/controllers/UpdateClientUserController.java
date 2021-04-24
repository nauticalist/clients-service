package io.seanapse.clients.jms.services.clients.cmd.api.clientuser.controllers;

import io.seanapse.clients.jms.services.clients.cmd.api.clientuser.commands.UpdateClientUserCommand;
import io.seanapse.clients.jms.services.clients.core.dto.BaseResponse;
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

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/clients")
public class UpdateClientUserController {
    private final CommandGateway commandGateway;

    @Autowired
    public UpdateClientUserController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PutMapping(path = "/{clientId}/users/{id}")
    @PreAuthorize("hasAnyRole('ROLE_CLIENTS_MANAGER')")
    public ResponseEntity<BaseResponse> updateClientUser(
            @PathVariable(value = "clientId") String clientId,
            @PathVariable(value = "id") String id,
            @Valid @RequestBody UpdateClientUserCommand command,
            @AuthenticationPrincipal Jwt principal) {
        String userId = (String) principal.getClaims().get("sub");

        try {
            command.setId(id);
            command.getClientUser().setClientId(clientId);
            command.getClientUser().setModifiedBy(userId);

            commandGateway.sendAndWait(command);

            return new ResponseEntity<>(new BaseResponse("Successfully updated user for client with id - " + clientId), HttpStatus.OK);
        } catch (Exception e) {
            var errorMessage = "Error while processing request to update user for client with id - " + clientId;
            log.error(e.toString());

            return new ResponseEntity<>(new BaseResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
