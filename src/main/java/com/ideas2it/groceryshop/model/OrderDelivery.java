package com.ideas2it.grocery.model;

import org.hibernate.annotations.Target;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orderDelivery")
public class OrderDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "is_delivered")
    private Boolean isDelivered;
    @Column(name = "delivery_date")
    private Date deliveryDate;

    @OneToOne(cascade = CascadeType.ALL)
    @Target(Order.class)
    private Order order;

    public OrderDelivery() {
    }

    public OrderDelivery(Integer id, Boolean isDelivered, Date deliveryDate) {
        this.id = id;
        this.isDelivered = isDelivered;
        this.deliveryDate = deliveryDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getDelivered() {
        return isDelivered;
    }

    public void setDelivered(Boolean delivered) {
        isDelivered = delivered;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }


}
