package com.abhishek.discountservice.service;

import com.abhishek.discountservice.dto.TaxSlabs;
import com.abhishek.discountservice.dto.Item;
import com.abhishek.discountservice.util.ReadUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TaxDiscountCalculatorService {

    private static Map<String, TaxSlabs> configValuesMap = new HashMap<>();

    static {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
//        byte[] configData = new byte[0];
        try {
            byte[] configData = ReadUtil.resourceBytes("config.json");
            configValuesMap = objectMapper.readValue(configData, new TypeReference<Map<String, TaxSlabs>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("Config value map - {}", configValuesMap);
    }

    public List<Item> calculateTaxAndDiscount(List<Item> itemList) throws IOException {


        for(Item item : itemList) {
            TaxSlabs taxSlabs = getTaxSlabForItem(item, configValuesMap);
            log.info("Tax slab for item - {}, slab - {}", item, taxSlabs);
            if (taxSlabs != null) {
                setFinalPriceInItem(taxSlabs, item);
            }
        }

        return itemList;
    }

    private void setFinalPriceInItem(TaxSlabs taxSlabs, Item item) {
        double taxValue = 0.0;
        if (taxSlabs.getType().equalsIgnoreCase("fixed")) {
            taxValue = taxSlabs.getTax();
        } else {
             taxValue = item.getBasePrice() * taxSlabs.getTax() * 0.01;
        }
        double discountValue = item.getBasePrice() * item.getDiscount() * 0.01;
        double finalPrice = item.getBasePrice() + taxValue - discountValue;
        item.setFinalPrice(finalPrice);
    }

    private TaxSlabs getTaxSlabForItem(Item item, Map<String, TaxSlabs> configValuesMap) {
        TaxSlabs taxSlabs = null;
        for(Map.Entry<String, TaxSlabs> entry : configValuesMap.entrySet()) {
            TaxSlabs taxSlabs1 = entry.getValue();
            if (taxSlabs1.getMinPrice() == 0 && taxSlabs == null) {
                taxSlabs = taxSlabs1;
                continue;
            }
            if (item.getBasePrice() >= taxSlabs1.getMinPrice()) {
                if (taxSlabs1.getMaxPrice() != null && item.getBasePrice() < taxSlabs1.getMaxPrice()) {
                    return taxSlabs1;
                } else if (taxSlabs1.getMaxPrice() == null) {
                    return taxSlabs1;
                }

            }

        }
        return taxSlabs;
    }
}
