package JavaDemo.repository;

/**
 * Order repository. Describes to all database operations with orders.
 * Created by Неволин on 27.11.2015.
 */

import JavaDemo.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
    Page<Order> findAllByOrderByIdDesc(Pageable pageable);
    Page<Order> findAllByInfoLikeOrderByIdDesc(Pageable pageable, String info);
}
