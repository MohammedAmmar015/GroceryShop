package com.ideas2it.groceryshop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.ideas2it.groceryshop.audit.Audit;

/**
 *
 * Role POJO is used Store and retrieve role data
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
@Table(name = "Role")
public class Role extends Audit{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Column(name = "is_active", nullable = false, columnDefinition = "TINYINT")
    private Boolean isActive = Boolean.TRUE;
}