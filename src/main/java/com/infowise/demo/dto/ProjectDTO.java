package com.infowise.demo.dto;

import com.infowise.demo.Entity.Project;

import java.time.LocalDateTime;

public record ProjectDTO(Long idx, String name, LocalDateTime startDate,
                         LocalDateTime endDate, Boolean isUse) {

    public static ProjectDTO of(Long idx, String name, LocalDateTime startDate,
                                LocalDateTime endDate, Boolean isUse){
        return new ProjectDTO(idx, name, startDate, endDate, isUse);
    }

    public static ProjectDTO fromEntity(Project project){
        return new ProjectDTO(
                project.getIdx(), project.getName(), project.getStartDate(),
                project.getEndDate(),project.getIsUse()
        );
    }

    public Project toEntity(){return Project.of(name, startDate, endDate, isUse);}
}
