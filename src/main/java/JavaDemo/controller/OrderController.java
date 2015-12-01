/**
 * This controller using for all actions with orders.
 */
package JavaDemo.controller;

import JavaDemo.domain.Order;
import JavaDemo.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;


@RestController
@RequestMapping(value = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    @Inject
    OrderRepository orderRepository;
    private final int pageSize = 5;

    @RequestMapping(value = "/page", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Order> getPage(@RequestParam int page, @RequestParam String infoSearchQuery) {
        if (infoSearchQuery.isEmpty()) {
            return orderRepository.findAllByOrderByIdDesc(new PageRequest(page, pageSize));
        }
        else {
            return orderRepository.findAllByInfoLikeOrderByIdDesc(
                    new PageRequest(page, pageSize), '%' + infoSearchQuery + '%'
            );
        }
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Order saveOrder(@RequestBody Order order) {
        return orderRepository.save(order);
    }


    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public void removeOrder(@RequestBody int id) {
        orderRepository.delete(id);
    }
}
