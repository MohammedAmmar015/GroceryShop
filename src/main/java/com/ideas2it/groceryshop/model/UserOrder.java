package com.ideas2it.groceryshop.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "user_order")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "ordered_date")
    @CreationTimestamp
    private Date orderedDate;
    @Column(name = "total_Price", nullable = false)
    private Float totalPrice;
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Date createdAt;
    @Column(name = "modified_at")
    @UpdateTimestamp
    private Date modifiedAt;
    @Column(name = "created_by", nullable = false)
    private Integer createdBy;
    @Column(name = "modified_by", nullable = false)
    private Integer modifiedBy;
    @OneToMany
    @JoinColumn(name = "order_id")
    private List<OrderDetails> orderDetails;
    @OneToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}