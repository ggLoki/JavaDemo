/**
 * This controller using for all actions with orders.
 */
package JavaDemo.controller;

import JavaDemo.domain.Order;
import JavaDemo.repository.OrderRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    @Inject
    OrderRepository orderRepository;
    private int pageSize = 5;
    private String infoSearchQuery= "";

    @RequestMapping(value = "/page/{page}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List getPage(@PathVariable("page") int page) {
        List<Order> result = new ArrayList<>();
        Iterable<Order> allOrders;
        if (this.infoSearchQuery.isEmpty()) {
            allOrders = orderRepository.findAllByOrderByIdDesc(new PageRequest(page, pageSize));
        }
        else {
            allOrders = orderRepository.findAllByInfoLikeOrderByIdDesc(
                    new PageRequest(page, pageSize), '%' + this.infoSearchQuery + '%');
        }
        for (Order o: allOrders) {
            result.add(o);
        }
        return result;
    }

    @RequestMapping(value = "/searchquery", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void setInfoSearchQuery(@RequestParam String infoSearchQuery) {
        this.infoSearchQuery = infoSearchQuery;
    }

    @RequestMapping(value = "/page/size", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public long getPageSize() {
        return this.pageSize;
    }

    @RequestMapping(value = "/count", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public long getOrdersCount() {
        if (this.infoSearchQuery.isEmpty()) {
            return orderRepository.count();
        }
        else {
            return orderRepository.countByInfoLike('%' + this.infoSearchQuery + '%');
        }
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Order saveOrder(@RequestBody Order order) {
        return orderRepository.save(order);
    }


    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public void removeOrder(@RequestBody Order order) {
        orderRepository.delete(order);
    }
}
