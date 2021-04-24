package io.seanapse.clients.jms.services.clients.query.api.clientuser.queries;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindClientUserByIdQuery {
    private String id;
}
