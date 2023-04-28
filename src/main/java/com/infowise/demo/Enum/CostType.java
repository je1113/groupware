package com.infowise.demo.Enum;

import lombok.Getter;

public enum CostType {
    SGA("판관비"),
    RESEARCH("연구비"),
    MANUFACTURING("제조원가");


    @Getter private final String description;

    CostType(String description){this.description=description;}

}
