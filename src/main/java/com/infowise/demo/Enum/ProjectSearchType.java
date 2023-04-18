package com.infowise.demo.Enum;

import lombok.Getter;

public enum ProjectSearchType {
    NAME("프로젝트명");

    @Getter private final String description;

    ProjectSearchType(String description){this.description=description;}
}
