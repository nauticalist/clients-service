package io.seanapse.clients.jms.services.clients.query.api.clientuser.repositories;

import io.seanapse.clients.jms.services.clients.core.clientuser.models.ClientUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientUserRepository extends MongoRepository<ClientUser, String> {
    void deleteClientUsersByClientId(String clientId);

    @Query("{ id: { $exists: true }}")
    List<ClientUser> findClientUsersByClientId(String clientId);

    @Query("{ id: { $exists: true }}")
    List<ClientUser> findClientUsersByPage(final Pageable page);

    @Query("{ id: { $exists: true }}")
    List<ClientUser> findClientUsersByClientIdAndByPage(String clientId, final Pageable page);

    @Query(value = "{'$or': [{'firstName': {$regex: '?1', $options: 'i'}}, {'lastName': {$regex: '?1', $options: 'i'}}, {'email': {$regex: '?1', $options: 'i'}}, {'phone': {$regex: '?1', $options: 'i'}}, {'description': {$regex: '?1', $options: 'i'}}]}")
    List<ClientUser> findByClientIdAndFilterRegex(String clientId, String filter);

    long countClientUserByClientId(String clientId);
}
