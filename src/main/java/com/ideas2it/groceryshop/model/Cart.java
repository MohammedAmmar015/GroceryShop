package com.ideas2it.groceryshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *     Cart Entity
 * </p>
 * @author Mohammed Ammar
 * @since 02-11-2022
 * @version 1.0
 */
@Entity
@Table(name = "cart")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "is_active = 1")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "total_price")
    private Float totalPrice;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private List<CartDetails> cartDetails;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at", nullable = false)
    private Date modifiedAt;

    @Column(name = "created_by", nullable = false)
    private Integer createdBy = 0;

    @Column(name = "modified_by", nullable = false)
    private Integer modifiedBy = 0;

    @Column(name = "is_active",
            nullable = false)
    private Boolean isActive = true;

    @OneToOne()
    @JoinColumn(name="user_id")
    private User user;

    @PostPersist
    public void setCreatedUser() {
        createdBy = user.getId();
    }
    @PostUpdate
    public void setModifiedUser() {
        modifiedBy = user.getId();
    }

}
