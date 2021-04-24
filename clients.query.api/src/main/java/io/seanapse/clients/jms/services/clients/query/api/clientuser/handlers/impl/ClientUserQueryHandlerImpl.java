package io.seanapse.clients.jms.services.clients.query.api.clientuser.handlers.impl;

import io.seanapse.clients.jms.services.clients.query.api.clientuser.dto.ClientUserResponse;
import io.seanapse.clients.jms.services.clients.query.api.clientuser.dto.PagedClientUserResponse;
import io.seanapse.clients.jms.services.clients.query.api.clientuser.handlers.ClientUserQueryHandler;
import io.seanapse.clients.jms.services.clients.query.api.clientuser.queries.FindClientUserByIdQuery;
import io.seanapse.clients.jms.services.clients.query.api.clientuser.queries.FindUsersByClientIdByPageQuery;
import io.seanapse.clients.jms.services.clients.query.api.clientuser.queries.FindUsersByClientIdQuery;
import io.seanapse.clients.jms.services.clients.query.api.clientuser.queries.SearchInClientUsersQuery;
import io.seanapse.clients.jms.services.clients.query.api.clientuser.repositories.ClientUserRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ClientUserQueryHandlerImpl implements ClientUserQueryHandler {
    private final ClientUserRepository clientUserRepository;

    @Autowired
    public ClientUserQueryHandlerImpl(ClientUserRepository clientUserRepository) {
        this.clientUserRepository = clientUserRepository;
    }

    @Override
    @QueryHandler
    public ClientUserResponse getClientUserById(FindClientUserByIdQuery query) {
        var user = clientUserRepository.findById(query.getId());
        return user.map(ClientUserResponse::new).orElse(null);
    }

    @Override
    @QueryHandler
    public ClientUserResponse getClientUsersByClientId(FindUsersByClientIdQuery query) {
        var users = new ArrayList<>(clientUserRepository.findClientUsersByClientId(query.getClientId()));
        return new ClientUserResponse(users);
    }

    @Override
    @QueryHandler
    public ClientUserResponse searchClientUsers(SearchInClientUsersQuery query) {
        var users = new ArrayList<>(clientUserRepository.findByClientIdAndFilterRegex(query.getClientId(), query.getFilter()));
        return new ClientUserResponse(users);
    }

    @Override
    @QueryHandler
    public PagedClientUserResponse getClientUsersByClientIdAndByPage(FindUsersByClientIdByPageQuery query) {
        PageRequest pageRequest = PageRequest.of(query.getPage(), query.getSize(), Sort.by("firstName").ascending());
        var totalNumberOfUsers = clientUserRepository.countClientUserByClientId(query.getClientId());
        var users = new ArrayList<>(clientUserRepository.findClientUsersByClientIdAndByPage(query.getClientId(), pageRequest));
        return new PagedClientUserResponse(pageRequest, users, totalNumberOfUsers);
    }
}
