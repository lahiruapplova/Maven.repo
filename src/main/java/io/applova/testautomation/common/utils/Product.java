package io.applova.testautomation.common.utils;

import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class Product {
    private List<String> taxes;
    private boolean active;
    private boolean alcoholicProduct;
    private boolean featured;
    private boolean autoRestockEnabled;
    private String productId;
    private String name;
    private String description;
    private List<String> categories;
    private double rating;
    private double taxPercentage;
    private int comments;
    private List<String> tags;
    private List<String> images;
    private List<String> thumbImages;
    private boolean deliverable;
    private Variants variants;
    private List<String> addOns;
    private boolean activeForKiosk;
    private boolean activeForOrderAhead;
    private boolean activeForOrderAheadWebstore;
    private boolean activeForDigitalDining;
    private boolean activeForPOSRegister;
    private String createdDate;
    private int priority;
    private List<String> displayDeviceIds;
}

@Data
class Variants {
    private String name;
    private String alternativeName;
    private List<Types> types;

}

@Data
class Types {
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

@Data
class Price {
    private Currency currency;
    private double amount;
}

@Data
class Currency {
    private String code;
    private String symbol;
}


