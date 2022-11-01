package com.ideas2it.groceryshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import javax.persistence.*;


/**
 *
 *  User POJO is used common attributes of Admin, Customer and DeliveryMan object to store
 *  and retrieve data
 *
 * @version 19.0 31-10-2022
 *
 * @author Rohit A P
 *
 */
@Entity
@Table(name = "User")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "mobile_number")
    private Long mobileNumber;
    @Column(name = "email")
    private String email;
    @ManyToOne()
    @JoinColumn(referencedColumnName = "id", columnDefinition = "role_id")
    private Role role;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Address> addresses;
}
