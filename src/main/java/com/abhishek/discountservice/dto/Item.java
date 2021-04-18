package com.abhishek.discountservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private String item;
    private double basePrice;
    private double discount;
    private double finalPrice;
}
