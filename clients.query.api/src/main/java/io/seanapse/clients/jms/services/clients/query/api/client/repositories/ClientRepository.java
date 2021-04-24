package io.seanapse.clients.jms.services.clients.query.api.client.repositories;

import io.seanapse.clients.jms.services.clients.core.client.models.Client;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends MongoRepository<Client, String> {
    @Query("{ id: { $exists: true }}")
    List<Client> findClientsByPage (final Pageable page);

    @Query(value = "{'$or': [{'companyName': {$regex: '?0', $options: 'i'}}, {'companyTitle': {$regex: '?0', $options: 'i'}}, {'vatNumber': {$regex: '?0', $options: 'i'}}, {'description': {$regex: '?0', $options: 'i'}}, {'address': {$regex: '?0', $options: 'i'}}]}")
    List<Client> findByFilterRegex(String filter);
}
