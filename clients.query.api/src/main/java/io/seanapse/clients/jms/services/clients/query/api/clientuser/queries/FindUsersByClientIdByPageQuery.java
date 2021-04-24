package io.seanapse.clients.jms.services.clients.query.api.clientuser.queries;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindUsersByClientIdByPageQuery {
    private String clientId;
    private int page;
    private int size;
}
