package io.seanapse.clients.jms.services.clients.query.api.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.seanapse.clients.jms.services.clients.core.client.models.Client;
import io.seanapse.clients.jms.services.clients.core.dto.BaseResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PagedClientResponse extends BaseResponse {
    private Pageable pageable;

    private List<Client> clients;

    private long totalNumberOfElements;

    public PagedClientResponse(String message) {
        super(message);
    }

    public PagedClientResponse(Pageable pageable, List<Client> clients, long totalNumberOfElements) {
        super(null);
        this.pageable = pageable;
        this.clients = clients;
        this.totalNumberOfElements = totalNumberOfElements;
    }

    public List<Client> getClients() {
        return clients;
    }

    @JsonProperty("totalCount")
    public long getTotalCount() {
        return totalNumberOfElements;
    }

    @JsonProperty("totalPages")
    public int getTotalPages() {
        double totalNumberOfPages = Math.floor(((double) totalNumberOfElements + (double) pageable.getPageSize() - 1) / (double) pageable.getPageSize());
        return (int) totalNumberOfPages;
    }


    @JsonProperty("currentPage")
    public int getCurrentPage() {
        return pageable.getPageNumber() + 1;
    }

    @JsonProperty("pageSize")
    public int getPageSize() {
        return pageable.getPageSize();
    }
}
