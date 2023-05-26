package com.infowise.demo.rep;

import com.infowise.demo.dto.WorkDTO;

import java.time.format.DateTimeFormatter;

public record WorkRep(
        Long idx,
        String memberName,
        String memberEmail,
        String costType,
        Long projectIdx,
        String projectName,
        String projectPeriod,
        Integer year,
        Integer month,
        Integer week,
        Float gongSoo
) {
    public static WorkRep fromDTO(WorkDTO dto){
        String period;
        String costTypeString;
        if(dto.projectDTO().costType()==null) {
            costTypeString = " ";
        }else{ costTypeString = dto.projectDTO().costType().getDescription();}
        period = dto.projectDTO().startDate().format(DateTimeFormatter.ofPattern("yy/MM/dd"))
                + " ~ " + dto.projectDTO().endDate().format(DateTimeFormatter.ofPattern("yy/MM/dd"));
        return new WorkRep(dto.idx(), dto.memberDTO().name(), dto.memberDTO().email(),
                costTypeString,dto.projectDTO().idx(), dto.projectDTO().name(),
                period, dto.year(), dto.month(), dto.week(), dto.gongSoo());
    }
}
