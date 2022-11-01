package com.ideas2it.grocery.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
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

    public Order() {}

    public Order(Integer id, Date orderedDate, Float totalPrice, Boolean isActive) {
        this.id = id;
        this.orderedDate = orderedDate;
        this.totalPrice = totalPrice;
        this.isActive = isActive;
        this.orderDetails = orderDetails;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(Date orderedDate) {
        this.orderedDate = orderedDate;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderedDate=" + orderedDate +
                ", totalPrice=" + totalPrice +
                ", isActive=" + isActive +
                ", orderDetails=" + orderDetails +
                '}';
    }
}