package com.ideas2it.groceryshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * It implements methods for add, view, delete and update operation for categories
 * </p>
 *
 * @ author Ruban
 * @ version 1.0 01/11/22
 *
 **/
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column (name = "name", length = 20, nullable = false)
    private String name;

    @OneToMany
    @JoinColumn(name = "parent_id")
    private List<Category> category;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name ="modified_at", nullable = false)
    private Date modifiedAt;

    @Column(name = "created_by", nullable = false)
    //@CreationTimestamp
    private int createdBy;

    @Column(name = "modified_by", nullable = false)
    private int modifiedBy;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;
}
