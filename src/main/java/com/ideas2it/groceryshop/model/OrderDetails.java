package com.ideas2it.groceryshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="order_details")
public class OrderDetails extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @Column(name = "price", nullable = false)
    private Float price;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

}
