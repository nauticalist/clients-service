package io.seanapse.clients.jms.services.clients.query.api.clientuser.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.seanapse.clients.jms.services.clients.core.clientuser.models.ClientUser;
import io.seanapse.clients.jms.services.clients.core.dto.BaseResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PagedClientUserResponse extends BaseResponse {
    private Pageable pageable;

    private List<ClientUser> clientUsers;

    private long totalNumberOfElements;

    public PagedClientUserResponse(String message) {
        super(message);
    }

    public PagedClientUserResponse(Pageable pageable, List<ClientUser> clientUsers, long totalNumberOfElements) {
        super(null);
        this.pageable = pageable;
        this.clientUsers = clientUsers;
        this.totalNumberOfElements = totalNumberOfElements;
    }

    public List<ClientUser> getClientUsers() {
        return clientUsers;
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
