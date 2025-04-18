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
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "products")
//@Data
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<WishList> getWishLists() {
        return wishLists;
    }

    public void setWishLists(List<WishList> wishLists) {
        this.wishLists = wishLists;
    }
}
