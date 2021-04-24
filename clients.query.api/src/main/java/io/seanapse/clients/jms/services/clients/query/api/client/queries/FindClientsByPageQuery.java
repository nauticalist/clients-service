package io.seanapse.clients.jms.services.clients.query.api.client.queries;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindClientsByPageQuery {
    private int page;
    private int size;
}
