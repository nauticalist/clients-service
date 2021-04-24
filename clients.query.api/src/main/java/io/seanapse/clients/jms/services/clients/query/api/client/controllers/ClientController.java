package io.seanapse.clients.jms.services.clients.query.api.client.controllers;

import io.seanapse.clients.jms.services.clients.query.api.client.dto.ClientResponse;
import io.seanapse.clients.jms.services.clients.query.api.client.dto.PagedClientResponse;
import io.seanapse.clients.jms.services.clients.query.api.client.queries.FindClientByIdQuery;
import io.seanapse.clients.jms.services.clients.query.api.client.queries.FindClientsByPageQuery;
import io.seanapse.clients.jms.services.clients.query.api.client.queries.FindClientsQuery;
import io.seanapse.clients.jms.services.clients.query.api.client.queries.SearchClientsQuery;
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
public class ClientController {
    private final QueryGateway queryGateway;

    @Autowired
    public ClientController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_CLIENTS_USER')")
    public ResponseEntity<ClientResponse> getClients() {
        try {
            var query = new FindClientsQuery();
            var response = queryGateway.query(query, ResponseTypes.instanceOf(ClientResponse.class)).join();

            if (response == null || response.getClients() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var errorMessage = "Failed to complete get all clients request";
            log.error(e.toString());
            return new ResponseEntity<>(new ClientResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_CLIENTS_USER')")
    public ResponseEntity<ClientResponse> getClientById(@PathVariable("id") String id) {
        try {
            var query = new FindClientByIdQuery(id);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(ClientResponse.class)).join();

            if (response == null || response.getClients() == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var errorMessage = "Failed to complete get client by id request";
            log.error(e.toString());
            return new ResponseEntity<>(new ClientResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/filter/{filter}")
    @PreAuthorize("hasAnyRole('ROLE_CLIENTS_USER')")
    public ResponseEntity<ClientResponse> searchClientsByFilter(@PathVariable("filter") String filter) {
        try {
            var query = new SearchClientsQuery(filter);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(ClientResponse.class)).join();

            if (response == null || response.getClients() == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var errorMessage = "Failed to complete search request";
            log.error(e.toString());
            return new ResponseEntity<>(new ClientResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/")
    @PreAuthorize("hasAnyRole('ROLE_CLIENTS_USER')")
    public ResponseEntity<PagedClientResponse> getClientsByPage(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                @RequestParam(name = "size", defaultValue = "20") int size) {
        try {
            var query = new FindClientsByPageQuery(page, size);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(PagedClientResponse.class)).join();

            if (response == null || response.getClients() == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var errorMessage = "Failed to complete get clients by page request";
            log.error(e.toString());
            return new ResponseEntity<>(new PagedClientResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
