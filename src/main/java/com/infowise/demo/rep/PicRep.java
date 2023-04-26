package com.infowise.demo.rep;

import com.infowise.demo.dto.PicDTO;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public record PicRep(
        Long idx,
        Long projectIdx,
        Long memberIdx,
        String projectName,
        String projectPeriod,
        String memberName,
        String memberGroup
) {

    public static PicRep fromDTO(PicDTO dto){
        String period;
        period = dto.projectDTO().startDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"))
        + " ~ " + dto.projectDTO().endDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
        return new PicRep(
                dto.idx(),
                dto.projectDTO().idx(),
                dto.memberDTO().idx(),
                dto.projectDTO().name(),
                period,
                dto.memberDTO().name(),
                dto.memberDTO().team()
       );
    }
}
