package io.applova.testautomation.common.utils;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
public class CartItem {
    private String productId;
    private String variantId;
    private String cartItemId;
    private String lineItemId;
    private int quantity;
    private List<String> addOnSubTypeIds;
    private String note;
    private int servingBatchNumber;
    private String cartSource;

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}

