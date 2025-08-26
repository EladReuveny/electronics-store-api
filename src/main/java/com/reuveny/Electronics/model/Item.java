/**
 * @package Electronics
 * @author Elad Reuveny
 *
 * Represents an item in a shopping cart or order, linking a product with quantity.
 */
package com.reuveny.Electronics.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JacksonXmlProperty(localName = "id")
    private Long id;

    @Column(nullable = false)
    @JacksonXmlProperty(localName = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(
            name = "product_id",
            nullable = false
    )
    @JacksonXmlProperty(localName = "product")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "shopping_cart_id")
    @JsonIgnore
    private ShoppingCart shoppingCart;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;
}
