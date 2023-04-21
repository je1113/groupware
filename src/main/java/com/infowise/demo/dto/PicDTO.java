package com.infowise.demo.dto;

import com.infowise.demo.Entity.Member;
import com.infowise.demo.Entity.Pic;
import com.infowise.demo.Entity.Project;

public record PicDTO(Long idx, MemberDTO memberDTO, ProjectDTO projectDTO ) {

    public static PicDTO of(Long idx, MemberDTO memberDTO, ProjectDTO projectDTO){
        return new PicDTO(idx, memberDTO, projectDTO);
    }

    public static PicDTO of(MemberDTO memberDTO, ProjectDTO projectDTO){
        return new PicDTO(null, memberDTO, projectDTO);
    }

    public static PicDTO fromEntity(Pic pic){
        return new PicDTO(
                pic.getIdx(),
                MemberDTO.fromEntity(pic.getMember()),
                ProjectDTO.fromEntity(pic.getProject())
        );
    }

    public Pic toEntity(Member member, Project project){
        return Pic.of(member, project);
    }
}
