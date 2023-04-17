package com.infowise.demo.dto;

import com.infowise.demo.Entity.Member;

public record MemberDTO(Long idx, String email, String pw, String name,
                        String team, String hp) {

    //DTO 생성
    public static MemberDTO of(Long idx, String email, String pw, String name,
                               String team, String hp){
        return new MemberDTO(idx, email, pw, name, team, hp);
    }

    // Emtity -> DTO
    public static MemberDTO fromEntity(Member member){
        return new MemberDTO(
                member.getIdx(), member.getEmail(), member.getPw(),
                member.getName(), member.getTeam(), member.getHp()
        );
    }


    //DTO -> Entity
    public Member toEntity(){ return Member.of( email, pw, name, team, hp);}
}
