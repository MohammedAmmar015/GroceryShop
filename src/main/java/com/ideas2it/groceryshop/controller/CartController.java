package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.model.Cart;
import com.ideas2it.groceryshop.repository.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CartController {
    @Autowired
    private CartRepo cartRepo;

    @GetMapping("/carts")
    public List<Cart> viewCarts() {
        return cartRepo.findAll();
    }
}
