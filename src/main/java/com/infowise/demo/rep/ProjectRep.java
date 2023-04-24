package com.infowise.demo.rep;

import com.infowise.demo.dto.ProjectDTO;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public record ProjectRep(Long idx, String name, String period, Boolean isUse) {
    public static ProjectRep fromDTO(ProjectDTO dto){
        String period;
        period = dto.startDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"))
        + " ~ "+ dto.endDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
        return new ProjectRep(
                dto.idx(),dto.name(), period, dto.isUse() );
    }
}
