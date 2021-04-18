package com.abhishek.discountservice.controller;

import com.abhishek.discountservice.dto.Item;
import com.abhishek.discountservice.service.TaxDiscountCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/tax-discount")
public class TaxDiscountController {


    @Autowired
    private TaxDiscountCalculatorService taxDiscountCalculatorService;

    @PostMapping("/calculate")
    public ResponseEntity<List<Item>> calculateTaxAndDiscount(@RequestBody List<Item> itemList) throws IOException {
        List<Item> finalItemResponseList = taxDiscountCalculatorService.calculateTaxAndDiscount(itemList);
        return new ResponseEntity<>(finalItemResponseList, HttpStatus.OK);
    }
}
