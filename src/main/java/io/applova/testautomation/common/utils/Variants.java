package io.applova.testautomation.common.utils;

import lombok.Data;

import java.util.List;

@Data
public class Variants {
    private String name;
    private String alternativeName;
    private List<Types> types;

}
