package com.infowise.demo.req;

import com.infowise.demo.Enum.CostType;
import com.infowise.demo.dto.MemberDTO;
import com.infowise.demo.dto.ProjectDTO;
import com.infowise.demo.dto.WorkDTO;

import java.time.LocalDate;

public record WorkReq(
        Long idx,
        Long projectIdx,
        Float gongSoo,
        Integer year,
        Integer month,
        Integer week
) {

    public static WorkReq of(Long idx, Long projectIdx, Float gongSoo,
                             Integer year, Integer month, Integer week){
        return new WorkReq(idx, projectIdx,gongSoo, year, month, week);
    }

    public WorkDTO toDto(MemberDTO memberDTO){
//        CostType costType1 = null;
//        if (costType == 0) {
//            costType1 = CostType.SGA;
//        } else if (costType == 1) {
//            costType1 = CostType.RESEARCH;
//        } else if (costType == 2) {
//            costType1 = CostType.MANUFACTURING;
//        }

        return WorkDTO.of(idx, memberDTO, ProjectDTO.of(projectIdx,null,null,null,null, null),
                gongSoo,year,month,week, LocalDate.now());
    }
}
