package JavaDemo.domain;

/**
 * Order entity. Contains only order info.
 * Created by Неволин on 27.11.2015.
 */


import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "simple_order")
public class Order {

    @Id
    @Column(name = "id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Generated(GenerationTime.INSERT)
    private int id;

    @Column(name = "info", nullable = false)
    private String info;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    @Cascade(value={org.hibernate.annotations.CascadeType.ALL})
    @OrderBy("id desc")
    @BatchSize(size = 100)
    private Set<Client> clients;

    public Order() {
    }

    public Order(String info) {
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        for (Client c: clients) {
            c.setOrder(this);
        }
        this.clients = clients;
    }
}
