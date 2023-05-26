package com.infowise.demo.Entity;


import com.infowise.demo.Enum.RoleType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Table(indexes={
        @Index(columnList = "email"),
        @Index(columnList = "team")
})
@Entity
@ToString(callSuper = true)
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long idx;
    @Setter private String email;
    @Setter private String pw;
    @Setter private String name;
    @Setter private String team;
    @Setter private String hp;
    @Setter private RoleType roleType;

    // 빈 생성자
    protected Member(){}
    // 등록할 때 필요한 정보
    private Member(String email, String pw, String name,
                   String team, String hp, RoleType roleType){
        this.email = email;
        this.pw = pw;
        this.name= name;
        this.team = team;
        this.hp = hp;
        this.roleType = roleType;
    }

    // 외부에서 사용할 때
    // static은 객체 생성없이 클래스를 통해 메서드 직접호출가능
    public static Member of(String email, String pw, String name,
                             String team, String hp, RoleType roleType){
        return new Member(email, pw, name, team, hp, roleType);
    }
}
