package io.seanapse.clients.jms.services.clients.query.api.client.dto;

import io.seanapse.clients.jms.services.clients.core.client.models.Client;
import io.seanapse.clients.jms.services.clients.core.dto.BaseResponse;

import java.util.ArrayList;
import java.util.List;

public class ClientResponse extends BaseResponse {
    private List<Client> clients;

    public ClientResponse(String message) {
        super(message);
    }

    public ClientResponse(List<Client> clients) {
        super(null);
        this.clients = clients;
    }

    public ClientResponse(String message, Client client) {
        super(message);
        this.clients = new ArrayList<>();
        this.clients.add(client);
    }

    public ClientResponse(Client client) {
        super(null);
        this.clients = new ArrayList<>();
        this.clients.add(client);
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }
}

