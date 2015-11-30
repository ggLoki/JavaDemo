package JavaDemo.repository;

/**
 * Client repository. Describes to all database operations with clients.
 * Created by Неволин on 27.11.2015.
 */

import JavaDemo.domain.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends CrudRepository<Client,Long> {
    List<Client> findByOrderId(int orderId);
}
