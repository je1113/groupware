package com.infowise.demo.rep;

import com.infowise.demo.dto.ProjectDTO;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record ProjectRep(Long idx, String name, LocalDateTime startDate,
                         LocalDateTime endDate, String period, Boolean isUse) {
    public static ProjectRep fromDTO(ProjectDTO dto){
        String period;
        period = dto.startDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"))
        + " ~ "+ dto.endDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
        return new ProjectRep(
                dto.idx(),dto.name(), dto.startDate(),dto.endDate(),period, dto.isUse() );
    }
}
