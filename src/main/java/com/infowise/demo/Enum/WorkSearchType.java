package com.infowise.demo.Enum;

import lombok.Getter;

public enum WorkSearchType {
    PROJECT("프로젝트명"),
    MEMBER("직원 이름");

    @Getter private final String description;

    WorkSearchType(String description){this.description=description;}
}
