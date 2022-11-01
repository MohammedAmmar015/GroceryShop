package com.ideas2it.groceryshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cart_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private float price;

    @ManyToOne(targetEntity = Products.class)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private List<Products> products;

}
