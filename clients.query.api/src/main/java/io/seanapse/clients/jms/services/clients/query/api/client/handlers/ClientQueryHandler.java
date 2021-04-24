package io.seanapse.clients.jms.services.clients.query.api.client.handlers;

import io.seanapse.clients.jms.services.clients.query.api.client.dto.ClientResponse;
import io.seanapse.clients.jms.services.clients.query.api.client.dto.PagedClientResponse;
import io.seanapse.clients.jms.services.clients.query.api.client.queries.FindClientByIdQuery;
import io.seanapse.clients.jms.services.clients.query.api.client.queries.FindClientsByPageQuery;
import io.seanapse.clients.jms.services.clients.query.api.client.queries.FindClientsQuery;
import io.seanapse.clients.jms.services.clients.query.api.client.queries.SearchClientsQuery;

public interface ClientQueryHandler {
    ClientResponse getClientById(FindClientByIdQuery query);

    ClientResponse getClients(FindClientsQuery query);

    ClientResponse searchClients(SearchClientsQuery query);

    PagedClientResponse getClientsByPage(FindClientsByPageQuery query);
}
