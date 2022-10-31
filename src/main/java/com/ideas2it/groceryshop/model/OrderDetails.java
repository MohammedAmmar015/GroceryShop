package com.ideas2it.grocery.model;

import javax.persistence.*;

@Entity
@Table(name="orderDetails")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "price")
    private Float price;

    @Column(name = "order_id")
    private Integer orderId;

    public OrderDetails() {}

    public OrderDetails(Integer id, Integer quantity, Float price) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public String toString(){
        return ("Quantity in Kilogram - "  + this.quantity + "/nPrice of a product " + this.price);
    }
}
