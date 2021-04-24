package io.seanapse.clients.jms.services.clients.query.api.client.handlers.impl;

import io.seanapse.clients.jms.services.clients.query.api.client.dto.ClientResponse;
import io.seanapse.clients.jms.services.clients.query.api.client.dto.PagedClientResponse;
import io.seanapse.clients.jms.services.clients.query.api.client.handlers.ClientQueryHandler;
import io.seanapse.clients.jms.services.clients.query.api.client.queries.FindClientByIdQuery;
import io.seanapse.clients.jms.services.clients.query.api.client.queries.FindClientsByPageQuery;
import io.seanapse.clients.jms.services.clients.query.api.client.queries.FindClientsQuery;
import io.seanapse.clients.jms.services.clients.query.api.client.queries.SearchClientsQuery;
import io.seanapse.clients.jms.services.clients.query.api.client.repositories.ClientRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ClientQueryHandlerImpl implements ClientQueryHandler {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientQueryHandlerImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    @QueryHandler
    public ClientResponse getClientById(FindClientByIdQuery query) {
        var client = clientRepository.findById(query.getId());
        return client.map(ClientResponse::new).orElse(null);
    }

    @Override
    @QueryHandler
    public ClientResponse getClients(FindClientsQuery query) {
        var clients = new ArrayList<>(clientRepository.findAll());
        return new ClientResponse(clients);
    }

    @Override
    @QueryHandler
    public ClientResponse searchClients(SearchClientsQuery query) {
        var clients = new ArrayList<>(clientRepository.findByFilterRegex(query.getFilter()));
        return new ClientResponse(clients);
    }

    @Override
    @QueryHandler
    public PagedClientResponse getClientsByPage(FindClientsByPageQuery query) {
        PageRequest pageRequest = PageRequest.of(query.getPage(), query.getSize(), Sort.by("companyName").ascending());
        var totalNumberOfClients = clientRepository.count();
        var clients = new ArrayList<>(clientRepository.findClientsByPage(pageRequest));
        return new PagedClientResponse(pageRequest, clients, totalNumberOfClients);
    }
}
