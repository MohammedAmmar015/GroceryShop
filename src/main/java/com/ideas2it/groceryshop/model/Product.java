package com.ideas2it.groceryshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.Transient;

/**
 * <p>
 *   It is the Model object for Product.
 * </p>
 *
 * @author Ruban
 * @version 1.0  01/11/22
 *
 **/
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name ="product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Name", length = 20, nullable = false)
    private String name;

    @Column(name ="price", nullable = false)
    private float price;

    @CreationTimestamp
    @Column(name = "created_at",  updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name ="modified_at", nullable = false)
    private Date modifiedAt;

    @Column(name = "created_by", nullable = false)
    private int createdBy;

    @Column(name = "modified_by", nullable = false)
    private int modifiedBy;

    @Column(name = "is_active")
    private boolean isActive = true;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="sub_category_id")
    private Category category;

    @Column(name ="unit")
    private String unit;

    @Column(name = "category_id")
    private int categoryId;

    @ManyToMany(mappedBy = "products")
    private List<StoreLocation> storeLocations;

}
