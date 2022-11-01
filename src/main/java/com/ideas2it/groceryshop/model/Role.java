package com.ideas2it.groceryshop.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String role;

}
