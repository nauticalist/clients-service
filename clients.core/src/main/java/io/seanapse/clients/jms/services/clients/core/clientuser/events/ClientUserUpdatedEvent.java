package io.seanapse.clients.jms.services.clients.core.clientuser.events;

import io.seanapse.clients.jms.services.clients.core.clientuser.models.ClientUser;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientUserUpdatedEvent {
    private String id;

    private ClientUser clientUser;
}
