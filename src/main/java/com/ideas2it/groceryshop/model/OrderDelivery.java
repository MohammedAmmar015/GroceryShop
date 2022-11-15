package com.ideas2it.groceryshop.model;

import com.ideas2it.groceryshop.audit.Audit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import java.util.Date;

/**
 * orderDelivery class contains all the delivery details
 *
 * @author Dhanalakshmi.M
 * @version 1.0
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_delivery")
public class OrderDelivery extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "is_delivered",nullable = false, columnDefinition = "TINYINT")
    private Boolean isDelivered = Boolean.FALSE;

    @Column(name = "delivered_date")
    private Date deliveryDate;

    @Column(name = "expected_date")
    private Date expectedDeliveryDate;

    @OneToOne(mappedBy = "orderDelivery")
    private UserOrder userOrder;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "shippingAddressId")
    private Address shippingAddress;

}
