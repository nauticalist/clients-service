package io.seanapse.clients.jms.services.clients.query.api.client.queries;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchClientsQuery {
    private String filter;
}
