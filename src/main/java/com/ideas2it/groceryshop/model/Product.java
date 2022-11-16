/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.model;

import com.ideas2it.groceryshop.audit.Audit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * <p>
 *   It is the Model pojo for Product.
 * </p>
 *
 * @author Ruban
 * @version 1.0
 * @since 03/11/22
 *
 **/
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="product")
public class Product extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Name", length = 20, nullable = false)
    private String name;

    @Column(name ="price", nullable = false)
    private float price;

    @Column(name = "is_active", columnDefinition = "TINYINT")
    private boolean isActive = true;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="sub_category_id")
    private Category category;

    @Column(name ="unit", length = 10)
    private String unit;

    @Column(name = "per_Head")
    private int perHead;
}
