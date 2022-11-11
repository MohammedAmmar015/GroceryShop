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
import com.ideas2it.groceryshop.model.Role;

/**
 *
 *  User POJO is used to store and retrieve data common attributes of
 *  Admin, Customer and DeliveryMan object
 *
 * @version 1.0 31-10-2022
 *
 * @author Rohit A P
 *
 */
@Entity
@Table(name = "User")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_name", length = 20 , nullable = false)
    private String userName;

    @Column(name = "first_name", length = 20 , nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 20 , nullable = false)
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "mobile_number", length = 10, nullable = false, unique=true)
    private Long mobileNumber;

    @Column(name = "email", length = 50, nullable = false, unique=true)
    private String email;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = Boolean.TRUE;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id", columnDefinition = "role_id")
    private Role role;
}