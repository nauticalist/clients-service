package io.seanapse.clients.jms.services.clients.cmd.api.client.controllers;

import io.seanapse.clients.jms.services.clients.cmd.api.client.commands.UpdateClientCommand;
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
public class UpdateClientController {
    private final CommandGateway commandGateway;

    @Autowired
    public UpdateClientController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_CLIENTS_MANAGER')")
    public ResponseEntity<BaseResponse> updateClient(@PathVariable(value = "id") String id,
                                                     @Valid @RequestBody UpdateClientCommand command,
                                                     @AuthenticationPrincipal Jwt principal) {
        String userId = (String) principal.getClaims().get("sub");

        try {
            command.setId(id);
            command.getClient().setModifiedBy(userId);

            commandGateway.sendAndWait(command);
            return new ResponseEntity<>(new BaseResponse("Successfully updated client"), HttpStatus.OK);
        } catch (Exception e) {
            var errorMessage = "Error while processing request to update client for id - " + id;
            log.error(e.toString());
            return new ResponseEntity<>(new BaseResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
