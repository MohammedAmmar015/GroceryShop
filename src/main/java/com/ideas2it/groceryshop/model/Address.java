package com.ideas2it.groceryshop.model;

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

import com.ideas2it.groceryshop.audit.Audit;

import com.ideas2it.groceryshop.model.User;

/**
 *
 * Address POJO is used Store and retrieve address data
 *
 * @version 1.0
 * @author Rohit A P
 * @since 31-10-2022
 */
@Entity
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@Table(name = "Address")
public class Address extends Audit{

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

    @Column(name = "is_active", nullable = false, columnDefinition = "TINYINT")
    private Boolean isActive = Boolean.TRUE;

    @Column(name = "is_default", nullable = false, columnDefinition = "TINYINT")
    private Boolean isDefault;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
}