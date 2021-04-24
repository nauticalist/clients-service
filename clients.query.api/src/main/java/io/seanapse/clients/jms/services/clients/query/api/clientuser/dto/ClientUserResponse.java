package io.seanapse.clients.jms.services.clients.query.api.clientuser.dto;

import io.seanapse.clients.jms.services.clients.core.clientuser.models.ClientUser;
import io.seanapse.clients.jms.services.clients.core.dto.BaseResponse;

import java.util.ArrayList;
import java.util.List;

public class ClientUserResponse extends BaseResponse {
    private List<ClientUser> clientUsers;

    public ClientUserResponse(String message) {
        super(message);
    }

    public ClientUserResponse(String message, List<ClientUser> clientUsers) {
        super(message);
        this.clientUsers = clientUsers;
    }

    public ClientUserResponse(List<ClientUser> clientUsers) {
        super(null);
        this.clientUsers = clientUsers;
    }

    public ClientUserResponse(String message, ClientUser clientUser) {
        super(message);
        this.clientUsers = new ArrayList<>();
        this.clientUsers.add(clientUser);
    }

    public ClientUserResponse(ClientUser clientUser) {
        super(null);
        this.clientUsers = new ArrayList<>();
        this.clientUsers.add(clientUser);
    }

    public List<ClientUser> getClientUsers() {
        return clientUsers;
    }

    public void setClientUsers(List<ClientUser> clientUsers) {
        this.clientUsers = clientUsers;
    }
}
