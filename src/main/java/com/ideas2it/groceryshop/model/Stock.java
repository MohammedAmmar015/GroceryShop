package com.ideas2it.groceryshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Date;

/**
 * <p>
 *     Stock Entity to holds stock details
 * </p>
 * @author Mohammed Ammar
 * @since 07-11-2022
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stock")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "available_stock")
    private Integer availableStock;

    @ManyToOne()
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "modified_at")
    @UpdateTimestamp
    private Date modifiedAt;

    @Column(name = "created_by")
    private Integer createdBy = 0;

    @Column(name = "modified_by")
    private Integer modifiedBy = 0;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private StoreLocation storeLocation;

}
