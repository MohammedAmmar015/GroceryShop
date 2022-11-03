package com.ideas2it.groceryshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Column(name ="price", nullable = false)
    private float price;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name ="modified_at", nullable = false)
    private Date modifiedAt;

    @Column(name = "created_by", nullable = false)
    private int createdBy;

    @Column(name = "modified_by", nullable = false)
    private int modifiedBy;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;
    
    @ManyToOne()
    @JoinColumn(name ="category_id")
    private Category category;

}
