package io.seanapse.clients.jms.services.clients.core.client.events;

import io.seanapse.clients.jms.services.clients.core.client.models.Client;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientUpdatedEvent {
    private String id;

    private Client client;
}
