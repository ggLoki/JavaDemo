package JavaDemo.repository;

/**
 * Order repository. Describes to all database operations with orders.
 * Created by Неволин on 27.11.2015.
 */

import JavaDemo.domain.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
}
