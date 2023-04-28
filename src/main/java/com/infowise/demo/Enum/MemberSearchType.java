package com.infowise.demo.Enum;

import lombok.Getter;

public enum MemberSearchType {
    EMAIL("이메일"),
    NAME("이름"),
    TEAM("그룹");

    @Getter private final String description;

    MemberSearchType(String description){
        this.description=description;
    }
}
