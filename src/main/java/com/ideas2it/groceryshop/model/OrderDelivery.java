package com.ideas2it.groceryshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_delivery")
public class OrderDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "is_delivered",nullable = false)
    private Boolean isDelivered;
    @Column(name = "delivered_date")
    @CreationTimestamp
    private Date deliveryDate;
    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;
    @Column(name = "modified_at")
    @CreationTimestamp
    private Date modifiedAt;
    @Column(name = "created_by", nullable = false)
    private Integer createdBy;
    @Column(name = "modified_by", nullable = false)
    private Integer modifiedBy;
    @OneToOne
    @JoinColumn(name = "order_id" )
    private UserOrder userOrder;
    @OneToOne
    @JoinColumn(name = "shippingAddressId")
    private Address shippingAddress;

}
