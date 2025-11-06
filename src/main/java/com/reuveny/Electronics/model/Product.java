/**
 * @package Electronics
 * @author Elad Reuveny
 *
 * Represents a product with attributes like name, description, price, stock, category, and image URL.
 */
package com.reuveny.Electronics.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JacksonXmlProperty(localName = "id")
    private Long id;

    @Column(nullable = false)
    @JacksonXmlProperty(localName = "name")
    private String name;

    @Column(
            nullable = true,
            columnDefinition = "TEXT"
    )
    @JacksonXmlProperty(localName = "description")
    private String description;

    @Column(nullable = false)
    @JacksonXmlProperty(localName = "price")
    private Double price;

    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    @JacksonXmlProperty(localName = "imgUrl")
    private String imgUrl;

    @Column(nullable = false)
    @JacksonXmlProperty(localName = "stockQuantity")
    private Integer stockQuantity;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @JacksonXmlProperty(localName = "category")
    private Category category;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<Item> items;

    @ManyToMany(mappedBy = "products")
    @JsonIgnore
    private List<WishList> wishLists;
}
