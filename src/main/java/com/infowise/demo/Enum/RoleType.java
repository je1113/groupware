package com.infowise.demo.Enum;

import lombok.Getter;

public enum RoleType {
    MANAGER("ROLE_MANAGER"),
    USER("ROLE_USER");

    @Getter private final String name;
    RoleType(String name){this.name = name;}
}
