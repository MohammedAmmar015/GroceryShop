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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

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
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="product")
//@Where(clause = "isActive='true")
public class Product extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Name", length = 20, nullable = false)
    private String name;

    @Column(name ="price", nullable = false)
    private float price;

    @Column(name = "is_active")
    private boolean isActive = true;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="sub_category_id")
    private Category category;

    @Column(name ="unit")
    private String unit;

    @Column(name = "image")
    private String image;

    @Column(name = "category_id")
    private int categoryId;

    @ManyToMany(mappedBy = "products")
    private List<StoreLocation> storeLocations;

}
