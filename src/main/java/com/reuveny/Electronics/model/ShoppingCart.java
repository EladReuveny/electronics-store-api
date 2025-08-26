/**
 * @package Electronics
 * @author Elad Reuveny
 *
 * Represents a user's shopping cart, holding items and total amount.
 */
package com.reuveny.Electronics.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "shopping_carts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Double totalAmount = 0.0;

    @OneToOne(mappedBy = "shoppingCart")
    @JsonIgnore
    private User user;

    @OneToMany(
            mappedBy = "shoppingCart",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Item> items;
}
