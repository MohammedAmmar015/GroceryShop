package com.ideas2it.groceryshop.model;

import java.util.Date;

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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 *
 * Role POJO is used Store and retrieve role data
 *
 * @version 19.0 31-10-2022
 *
 * @author Rohit A P
 *
 */
@Entity
@Table(name = "Role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", length = 15, nullable = false)
    private String name;
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;
    @Column(name = "modified_at", nullable = false)
    @UpdateTimestamp
    private Date ModifiedAt;
    @Column(name ="created_by", nullable = false)
    private Integer createdBy = 1;
    @Column(name = "modified_by", nullable = false)
    private Integer modifiedBY = 1;
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = Boolean.TRUE;
}
