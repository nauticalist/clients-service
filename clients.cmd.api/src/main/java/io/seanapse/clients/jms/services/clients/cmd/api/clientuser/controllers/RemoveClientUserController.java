package io.seanapse.clients.jms.services.clients.cmd.api.clientuser.controllers;

import io.seanapse.clients.jms.services.clients.cmd.api.clientuser.commands.RemoveClientUserCommand;
import io.seanapse.clients.jms.services.clients.core.dto.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/clients")
public class RemoveClientUserController {
    private final CommandGateway commandGateway;

    @Autowired
    public RemoveClientUserController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @DeleteMapping(path = "/{clientId}/users/{id}")
    @PreAuthorize("hasAnyRole('ROLE_CLIENTS_MANAGER')")
    public ResponseEntity<BaseResponse> removeClientUser(
            @PathVariable(value = "clientId") String clientId,
            @PathVariable(value = "id") String id) {
        try {
            commandGateway.sendAndWait(new RemoveClientUserCommand(id));

            return new ResponseEntity<>(new BaseResponse("Successfully removed user from client"), HttpStatus.OK);
        } catch (Exception e) {
            var errorMessage = "Error while processing request to remove user from client with id - " + clientId;
            log.error(e.toString());
            return new ResponseEntity<>(new BaseResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
