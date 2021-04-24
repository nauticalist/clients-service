package io.seanapse.clients.jms.services.clients.query.api.clientuser.controllers;

import io.seanapse.clients.jms.services.clients.query.api.clientuser.dto.ClientUserResponse;
import io.seanapse.clients.jms.services.clients.query.api.clientuser.dto.PagedClientUserResponse;
import io.seanapse.clients.jms.services.clients.query.api.clientuser.queries.FindClientUserByIdQuery;
import io.seanapse.clients.jms.services.clients.query.api.clientuser.queries.FindUsersByClientIdByPageQuery;
import io.seanapse.clients.jms.services.clients.query.api.clientuser.queries.FindUsersByClientIdQuery;
import io.seanapse.clients.jms.services.clients.query.api.clientuser.queries.SearchInClientUsersQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/clients")
public class ClientUserControllers {
    private final QueryGateway queryGateway;

    @Autowired
    public ClientUserControllers(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping(path = "/{clientId}/users")
    @PreAuthorize("hasAnyRole('ROLE_CLIENTS_USER')")
    public ResponseEntity<ClientUserResponse> getUsersForClient(@PathVariable(value = "clientId") String clientId) {
        try {
            var query = new FindUsersByClientIdQuery(clientId);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(ClientUserResponse.class)).join();

            if (response == null || response.getClientUsers() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var errorMessage = "Failed to complete get users for client request";
            log.error(e.toString());
            return new ResponseEntity<>(new ClientUserResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{clientId}/users/{id}")
    @PreAuthorize("hasAnyRole('ROLE_CLIENTS_USER')")
    public ResponseEntity<ClientUserResponse> getClientUserById(
            @PathVariable(value = "clientId") String clientId,
            @PathVariable(value = "id") String id) {
        try {
            var query = new FindClientUserByIdQuery(id);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(ClientUserResponse.class)).join();

            if (response == null || response.getClientUsers() == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var errorMessage = "Failed to complete get client user request";
            log.error(e.toString());
            return new ResponseEntity<>(new ClientUserResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{clientId}/users/bypage/")
    @PreAuthorize("hasAnyRole('ROLE_CLIENTS_USER')")
    public ResponseEntity<PagedClientUserResponse> getUsersForClientByPage(@PathVariable(value = "clientId") String clientId,
                                                                           @RequestParam(name = "page", defaultValue = "0") int page,
                                                                           @RequestParam(name = "size", defaultValue = "20") int size) {
        try {
            var query = new FindUsersByClientIdByPageQuery(clientId, page, size);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(PagedClientUserResponse.class)).join();

            if (response == null || response.getClientUsers() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var errorMessage = "Failed to complete get users for client by page request";
            log.error(e.toString());
            return new ResponseEntity<>(new PagedClientUserResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{clientId}/users/filter/{filter}")
    @PreAuthorize("hasAnyRole('ROLE_CLIENTS_USER')")
    public ResponseEntity<ClientUserResponse> searchInClientUsers(
            @PathVariable(value = "clientId") String clientId,
            @PathVariable(value = "filter") String filter) {
        try {
            var query = new SearchInClientUsersQuery(clientId, filter);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(ClientUserResponse.class)).join();

            if (response == null || response.getClientUsers() == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var errorMessage = "Failed to complete search request";
            log.error(e.toString());
            return new ResponseEntity<>(new ClientUserResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
