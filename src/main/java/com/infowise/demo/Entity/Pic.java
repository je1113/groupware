package com.infowise.demo.Entity;


import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Entity
@ToString()
public class Pic {
    //PIC = person in charge = 담당자
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long idx;
    @ManyToOne @JoinColumn(name="member_idx") Member member;
    @ManyToOne @JoinColumn(name="project_idx") Project project;

    protected Pic(){}

    private Pic( Member Member, Project project){
        this.member=Member;
        this.project=project;
    }

    public static Pic of( Member member, Project project){
        return new Pic( member, project);
    }

}
