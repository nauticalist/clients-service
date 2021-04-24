package io.seanapse.clients.jms.services.clients.core.clientuser.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "clientusers")
public class ClientUser {
    @Id
    private String id;

    private String clientId;

    @TextIndexed
    @NotNull(message = "First name must be provided.")
    @Size(min=2, max=200, message = "First name must be minimum 2 maximum 200 characters.")
    private String firstName;

    @TextIndexed
    @NotNull(message = "Last name must be provided.")
    @Size(min=2, max=200, message = "Last name must be minimum 2 maximum 200 characters.")
    private String lastName;

    @TextIndexed
    @Email(message = "Email address is not valid.")
    @NotNull(message = "Email address must be provided.")
    @Size(min=2, max=200, message = "Email address must be minimum 2 maximum 200 characters.")
    private String email;

    private String phone;

    private String description;

    private Date createdAt;

    private String createdBy;

    private Date modifiedAt;

    private String modifiedBy;
}