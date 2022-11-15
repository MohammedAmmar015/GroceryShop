package com.ideas2it.groceryshop.model;

import com.ideas2it.groceryshop.audit.Audit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
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
public class Cart extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "total_price", nullable = false, precision = 2)
    private Float totalPrice;

    @Column(name = "total_quantity", nullable = false)
    private Integer totalQuantity;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private List<CartDetails> cartDetails;

    @Column(name = "is_active",
            nullable = false,
            columnDefinition = "TINYINT")
    private Boolean isActive = true;

    @ManyToOne()
    @JoinColumn(name="user_id")
    private User user;

}
