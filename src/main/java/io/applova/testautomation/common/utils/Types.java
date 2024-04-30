package io.applova.testautomation.common.utils;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Types {
    private String name;
    private String alternativeName;
    private String sku;
    private Price price;
    private String description;
    private List<String> durationGroupPrices;
    private String promotionalPrice;
    private List<String> disabledGroups;
    private String displaySku;
    private Map<String, Integer> inventoryDetails;
}
