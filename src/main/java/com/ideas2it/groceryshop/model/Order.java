package com.ideas2it.groceryshop.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import com.ideas2it.groceryshop.model.OrderDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_order")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "ordered_date")
    private Date orderedDate;
    @Column(name = "total_Price")
    private Float totalPrice;
    @Column(name = "is_active")
    private Boolean isActive;
    @OneToMany
    @JoinColumn(name = "order_id")
    private List<OrderDetails> orderDetails;
    @OneToOne
    @JoinColumn(name = "order_id" )
    private OrderDelivery orderDelivery;

}