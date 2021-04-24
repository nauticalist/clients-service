package io.seanapse.clients.jms.services.clients.query.api.clientuser.handlers;

import io.seanapse.clients.jms.services.clients.query.api.clientuser.dto.ClientUserResponse;
import io.seanapse.clients.jms.services.clients.query.api.clientuser.dto.PagedClientUserResponse;
import io.seanapse.clients.jms.services.clients.query.api.clientuser.queries.FindClientUserByIdQuery;
import io.seanapse.clients.jms.services.clients.query.api.clientuser.queries.FindUsersByClientIdByPageQuery;
import io.seanapse.clients.jms.services.clients.query.api.clientuser.queries.FindUsersByClientIdQuery;
import io.seanapse.clients.jms.services.clients.query.api.clientuser.queries.SearchInClientUsersQuery;

public interface ClientUserQueryHandler {
    ClientUserResponse getClientUserById(FindClientUserByIdQuery query);

    ClientUserResponse getClientUsersByClientId(FindUsersByClientIdQuery query);

    ClientUserResponse searchClientUsers(SearchInClientUsersQuery query);

    PagedClientUserResponse getClientUsersByClientIdAndByPage(FindUsersByClientIdByPageQuery query);
}
