package io.seanapse.clients.jms.services.clients.cmd.api.client.controllers;

import io.seanapse.clients.jms.services.clients.cmd.api.client.commands.RemoveClientCommand;
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
public class RemoveClientController {
    private final CommandGateway commandGateway;

    @Autowired
    public RemoveClientController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_CLIENTS_MANAGER')")
    public ResponseEntity<BaseResponse> removeClient(@PathVariable(value = "id") String id) {

        try {
            commandGateway.sendAndWait(new RemoveClientCommand(id));

            return new ResponseEntity<>(new BaseResponse("Successfully removed client"), HttpStatus.OK);
        } catch (Exception e) {
            var errorMessage = "Error while processing request to remove client for id - " + id;
            log.error(e.toString());
            return new ResponseEntity<>(new BaseResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
