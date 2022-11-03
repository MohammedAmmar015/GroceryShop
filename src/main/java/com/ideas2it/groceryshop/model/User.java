package com.ideas2it.groceryshop.model;

import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.ideas2it.groceryshop.model.Address;
import com.ideas2it.groceryshop.model.Cart;
import com.ideas2it.groceryshop.model.OrderDelivery;
import com.ideas2it.groceryshop.model.UserOrder;

/**
 *
 *  User POJO is used to store and retrieve data common attributes of
 *  Admin, Customer and DeliveryMan object
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
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", length = 20 ,nullable = false)
    private String name;
    @Column(name = "mobile_number", length = 10, nullable = false)
    private Long mobileNumber;
    @Column(name = "email", length = 50, nullable = false)
    private String email;
    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Date createdAt;
    @Column(name = "modified_at", nullable = false)
    @CreationTimestamp
    private Date ModifiedAt;
    @Column(name ="created_by", nullable = false)
    private Integer createdBy;
    @Column(name = "modified_by", nullable = false)
    private Integer modifiedBY;
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id", columnDefinition = "role_id")
    private Role role;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Address> addresses;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<UserOrder> orders;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Cart> carts;
}