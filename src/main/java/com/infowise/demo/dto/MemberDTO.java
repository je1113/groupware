package com.infowise.demo.dto;

import com.infowise.demo.Entity.Member;
import com.infowise.demo.Enum.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public record MemberDTO(Long idx, String email, String pw, String name,
                        String team, String hp, RoleType roleType) {

    //DTO 생성
    public static MemberDTO of(Long idx, String email, String pw, String name,
                               String team, String hp, RoleType roleType){
        return new MemberDTO(idx, email, pw, name, team, hp, roleType);
    }

    // Emtity -> DTO
    public static MemberDTO fromEntity(Member member){
        return new MemberDTO(
                member.getIdx(), member.getEmail(), member.getPw(),
                member.getName(), member.getTeam(), member.getHp(), member.getRoleType()
        );
    }


    //DTO -> Entity
    public Member toEntity(String encodePw){
        return Member.of( email, encodePw, name, team, hp, roleType);}
}
