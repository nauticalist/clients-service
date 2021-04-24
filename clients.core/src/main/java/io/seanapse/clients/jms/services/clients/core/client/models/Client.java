package io.seanapse.clients.jms.services.clients.core.client.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "clients")
public class Client {
    @Id
    private String id;

    @TextIndexed
    @NotNull(message = "Company name must be provided.")
    @Size(min=2, max=200, message = "Company name must be minimum 2 maximum 200 characters.")
    private String companyName;

    @TextIndexed
    @NotNull(message = "Company title must be provided.")
    @Size(min=2, max=200, message = "Company title must be minimum 2 maximum 200 characters.")
    private String  companyTitle;

    private ClientType clientType;

    private String phone;

    private String fax;

    @TextIndexed
    @NotNull(message = "Address must be provided.")
    @Size(min=2, max=200, message = "Address must be minimum 2 maximum 200 characters.")
    private String address;

    @TextIndexed
    @NotNull(message = "Vat number must be provided.")
    @Size(min=2, max=200, message = "Vat number must be minimum 2 maximum 200 characters.")
    private String vatNumber;

    @TextIndexed
    @NotNull(message = "Vat office must be provided.")
    @Size(min=2, max=200, message = "Vat office must be minimum 2 maximum 200 characters.")
    private String vatOffice;

    private String description;

    private Date createdAt;

    private String createdBy;

    private Date modifiedAt;

    private String modifiedBy;
}
