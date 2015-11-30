package JavaDemo.domain;

/**
 * Order entity. Contains only order info.
 * Created by Неволин on 27.11.2015.
 */

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;

@Entity(name = "simple_order")
public class Order {

    @Id
    @Column(name = "id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Generated(GenerationTime.INSERT)
    private Long id;

    @Column(name = "info", nullable = false)
    private String info;

    public Order() {
    }

    public Order(String info) {
        this.info = info;
    }

    public Long getId() {
        return id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
