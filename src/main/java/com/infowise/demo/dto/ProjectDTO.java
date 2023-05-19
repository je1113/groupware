package com.infowise.demo.dto;

import com.infowise.demo.Entity.Project;
import com.infowise.demo.Enum.CostType;

import java.time.LocalDateTime;

public record ProjectDTO(Long idx, String name, LocalDateTime startDate,
                         LocalDateTime endDate, Boolean isUse, CostType costType) {

    public static ProjectDTO of(Long idx, String name, LocalDateTime startDate,
                                LocalDateTime endDate, Boolean isUse, CostType costType){
        return new ProjectDTO(idx, name, startDate, endDate, isUse, costType);
    }

    public static ProjectDTO fromEntity(Project project){
        return new ProjectDTO(
                project.getIdx(), project.getName(), project.getStartDate(),
                project.getEndDate(),project.getIsUse(), project.getCostType()
        );
    }

    public Project toEntity(){return Project.of(name, startDate, endDate, isUse, costType);}
}
