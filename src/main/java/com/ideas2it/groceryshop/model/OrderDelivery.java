package com.ideas2it.groceryshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Target;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

}
