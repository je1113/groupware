package com.infowise.demo.rep;

import com.infowise.demo.dto.ProjectDTO;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record ProjectRep(Long idx, String name, LocalDateTime startDate,
                         LocalDateTime endDate, String period, Boolean isUse, String costType) {
    public static ProjectRep fromDTO(ProjectDTO dto){
        String period;
        period = dto.startDate().format(DateTimeFormatter.ofPattern("yy/MM/dd"))
        + " ~ "+ dto.endDate().format(DateTimeFormatter.ofPattern("yy/MM/dd"));

        String costTypeString;
        if(dto.costType() == null){costTypeString = " ";}
        else{costTypeString = dto.costType().getDescription();}
        return new ProjectRep(
                dto.idx(),dto.name(), dto.startDate(),dto.endDate(),period, dto.isUse(), costTypeString);
    }
}
