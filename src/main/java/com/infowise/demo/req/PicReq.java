package com.infowise.demo.req;

import com.infowise.demo.dto.MemberDTO;
import com.infowise.demo.dto.PicDTO;
import com.infowise.demo.dto.ProjectDTO;

public record PicReq(
        Long memberIdx,
        String memberName,
        Long projectIdx

) {

    public static PicReq of( Long memberIdx, String memberName, Long projectIdx){
        return new PicReq(memberIdx, memberName, projectIdx);
    }

    public PicDTO toDto(MemberDTO memberDTO, ProjectDTO projectDTO){
        return PicDTO.of(memberDTO, projectDTO);
    }
}
