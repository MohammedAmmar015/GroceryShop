package com.ideas2it.groceryshop.model;

import java.util.List;

public class Cart {
    private int id;
    private Float totalPrice;
    private boolean isActive;
    private List<CartDetails> cartDetails;

    public Cart(int id, Float totalPrice, boolean isActive, List<CartDetails> cartDetails) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.isActive = isActive;
        this.cartDetails = cartDetails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<CartDetails> getCartDetails() {
        return cartDetails;
    }

    public void setCartDetails(List<CartDetails> cartDetails) {
        this.cartDetails = cartDetails;
    }
}
