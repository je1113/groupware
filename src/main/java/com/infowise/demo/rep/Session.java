package com.infowise.demo.rep;

import com.infowise.demo.dto.InfoWisePrincipal;

public record Session(String email, String name, String roleType) {
    public static Session fromDTO(InfoWisePrincipal dto){
        return new Session(dto.email(), dto.name(), dto.roleType().toString());
    }
}
