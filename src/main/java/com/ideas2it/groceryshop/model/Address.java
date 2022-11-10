package com.ideas2it.groceryshop.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.ideas2it.groceryshop.model.User;

/**
 *
 * Address POJO is used Store and retrieve address data
 *
 * @version 19.0 31-10-2022
 *
 * @author Rohit A P
 *
 */
@Entity
@Table(name = "Address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "street", length = 30, nullable = false)
    private String street;

    @Column(name = "area", length = 30, nullable = false)
    private String area;

    @Column(name = "pin_code", length = 6, nullable = false)
    private Integer pinCode;

    @Column(name = "land_mark", length = 30, nullable = false)
    private String landMark;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "modified_at", nullable = false)
    @UpdateTimestamp
    private Date modifiedAt;

    @Column(name ="created_by", nullable = false)
    private Integer createdBy = 1;

    @Column(name = "modified_by", nullable = false)
    private Integer modifiedBy = 1;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = Boolean.TRUE;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault = Boolean.TRUE;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
}