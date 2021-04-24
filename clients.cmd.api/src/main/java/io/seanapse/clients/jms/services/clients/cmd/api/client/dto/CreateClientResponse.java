package io.seanapse.clients.jms.services.clients.cmd.api.client.dto;

import io.seanapse.clients.jms.services.clients.core.dto.BaseResponse;

public class CreateClientResponse extends BaseResponse {
    private String id;

    public CreateClientResponse(String message, String id) {
        super(message);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
