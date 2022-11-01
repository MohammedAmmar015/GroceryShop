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

    @Column(name = "name", length = 20)
    private String name;

    @Column(name ="price")
    private float price;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name ="modified_at")
    private Date modifiedAt;

    @Column(name = "created_by")
    private int createdBy;

    @Column(name = "modified_by")
    private int modifiedBy;

    @Column(name = "is_active")
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name ="sub_category_id", referencedColumnName = "id")
    private SubCategory subCategory;

}
