package com.ideas2it.groceryshop.model;

import jdk.jfr.Timestamp;
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

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category category;

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

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

}
