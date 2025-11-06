/**
 * @package Electronics
 * @author Elad Reuveny
 *
 * Represents a registered user with email, password, address, phone, role, wishlist, shopping cart, and orders.
 */
package com.reuveny.Electronics.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JacksonXmlProperty(localName = "id")
    private Long id;

    @Column(
            unique = true,
            nullable = false
    )
    @JacksonXmlProperty(localName = "email")
    private String email;

    @Column(nullable = false)
    @JacksonXmlProperty(localName = "password")
    private String password;

    @Column(nullable = false)
    @JacksonXmlProperty(localName = "address")
    private String address;

    @Column(nullable = false)
    @JacksonXmlProperty(localName = "phone")
    private String phone;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role = Role.CUSTOMER;

    @OneToOne(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(
            name = "wish_list_id",
            nullable = false
    )
    private WishList wishList;

    @OneToOne(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(
            name = "shopping_cart_id",
            nullable = false
    )
    private ShoppingCart shoppingCart;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JacksonXmlProperty(localName = "orders")
    private List<Order> orders;
}
