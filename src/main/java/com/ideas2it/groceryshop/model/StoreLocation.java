package com.ideas2it.groceryshop.model;

import com.ideas2it.groceryshop.audit.Audit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * <p>
 *     Store Location Entity to hold location details
 * </p>
 * @author Mohammed Ammar
 * @since 02-11-2022
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "store_location")
public class StoreLocation extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "pin_code", nullable = false)
    private Integer pinCode;

    @Column(name = "area", length = 20,
            nullable = false, unique = true)
    private String area;

    @Column(name = "is_active",
            nullable = false,
            columnDefinition = "TINYINT")
    private Boolean isActive = true;

    @ManyToMany
    @JoinTable(name = "stock",
            joinColumns = @JoinColumn(name = "location_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;
}
