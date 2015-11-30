/**
 * Created by Неволин on 29.11.2015.
 *
 * This model using to unite orders & clients.
 * Here should be a little more logic.
 */
package JavaDemo.model;

import JavaDemo.domain.Client;
import JavaDemo.domain.Order;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DealModel implements Serializable {
    private List<Client> clients;
    private Order order;


    public DealModel(Order order, List<Client> clients) {
        this.order = order;
        this.clients = clients;
    }

    /**
     * Converts this model to map. It's need to use this map like JSON response
     * @return Map Map, where
     * · "order" key contains order data
     * · "client" key contains order clients
     */
    public Map toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("clients", clients);
        result.put("order", order);
        return result;
    }
}
