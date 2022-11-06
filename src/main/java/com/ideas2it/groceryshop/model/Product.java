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

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @CreationTimestamp
    @Column(name ="modified_at", nullable = false)
    private Date modifiedAt;

    @Column(name = "created_by", nullable = false)
    private int createdBy = 1;

    @Column(name = "modified_by", nullable = false)
    private int modifiedBy = 1;

    @Column(name = "is_active")
    private boolean isActive = true;
    
    @ManyToOne
    @JoinColumn(name ="sub_category_id")
    private Category category;

    @Column(name ="unit")
    private String unit;

    @Column(name = "category_id")
    private int categoryId;

}
