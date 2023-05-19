package com.infowise.demo.dto;

import com.infowise.demo.Entity.Member;
import com.infowise.demo.Entity.Project;
import com.infowise.demo.Entity.Work;
import com.infowise.demo.Enum.CostType;
import com.infowise.demo.req.WorkReq;

import java.time.LocalDate;


public record WorkDTO(Long idx, MemberDTO memberDTO, ProjectDTO projectDTO,
                      Float gongSoo, Integer year, Integer month, Integer week, LocalDate date) {
    public static WorkDTO of(Long idx, MemberDTO memberDTO, ProjectDTO projectDTO,
                             Float gongSoo, Integer year, Integer month, Integer week, LocalDate date){
        return new WorkDTO(idx, memberDTO, projectDTO, gongSoo, year, month, week, date);
    }

    public static WorkDTO fromEntity(Work work){
        return new WorkDTO(
                work.getIdx(), MemberDTO.fromEntity(work.getMember()),
                ProjectDTO.fromEntity(work.getProject()),
                work.getGongSoo(), work.getYear(),
                work.getMonth(), work.getWeek(), work.getDate()
        );
    }

    public Work toEntity(Member member, Project project){
        return Work.of(member, project, gongSoo, year, month, week, date);
    }

}
