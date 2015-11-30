/**
 * This controller using for all actions with orders & deals.
 * It a little ambiguous and should be refactored, I think.
 */
package JavaDemo.controller;

import JavaDemo.domain.Order;
import JavaDemo.model.DealModel;
import JavaDemo.repository.ClientRepository;
import JavaDemo.repository.OrderRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    @Inject
    OrderRepository orderRepository;

    @Inject
    ClientRepository clientRepository;

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List getAll() {
        List<Map> result = new ArrayList<>();
        Iterable<Order> allOrders = orderRepository.findAll();

        for (Order order : allOrders) {
            DealModel deal = new DealModel(order, clientRepository.findByOrderId(order.getId().intValue()));
            result.add(deal.toMap());
        }

        return result;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Order saveOrder(@RequestBody Order order) {
        return orderRepository.save(order);
    }


    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public void removeOrder(@RequestBody Order order) {
        // This shit is needed, because normal deleteByOrderId don't working. Dunno, why.
        clientRepository.delete(
                clientRepository.findByOrderId(order.getId().intValue())
        );
        orderRepository.delete(order);
    }
}
