package com.abhishek.discountservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxSlabs {

    private Integer minPrice;
    private Integer maxPrice;
    private String type;
    private Integer tax;
}
