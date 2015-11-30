/**
 * This GREAT controller using only to save clients.
 * Maybe later it will be using more... but later.
 */
package JavaDemo.controller;

import JavaDemo.domain.Client;
import JavaDemo.repository.ClientRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;


@RestController
@RequestMapping(value = "/api/clients", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientController {

    @Inject
    ClientRepository clientRepository;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Client saveClient(@RequestBody Client client) {
        return clientRepository.save(client);
    }

}
