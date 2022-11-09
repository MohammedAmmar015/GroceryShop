package com.ideas2it.groceryshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;

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
public class StoreLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "pin_code")
    private Integer pinCode;

    @Column(name = "area", length = 20)
    private String area;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "modified_at")
    @UpdateTimestamp
    private Date modifiedAt;

    @Column(name = "created_by")
    @CreatedBy
    private Integer createdBy;

    @Column(name = "modified_by")
    @LastModifiedBy
    private Integer modifiedBy;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
