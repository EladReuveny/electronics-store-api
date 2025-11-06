/**
 * @package Electronics
 * @author Elad Reuveny
 */
package com.reuveny.Electronics.dto;

import com.reuveny.Electronics.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductUpdateDto {
    private String name;

    private String description;

    private Double price;

    private String imgUrl;

    private Integer stockQuantity;

    private Category category;
}
