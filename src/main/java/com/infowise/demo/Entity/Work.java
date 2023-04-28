package com.infowise.demo.Entity;

import com.infowise.demo.Enum.CostType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Table
@Entity
@ToString(callSuper = true)
public class Work {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long idx;
    @ManyToOne @JoinColumn(name="member_idx") Member member;
    @ManyToOne @JoinColumn(name="project_idx") Project project;
    @Setter CostType costType;
    @Setter Float gongSoo;
    Integer year;
    Integer month;
    Integer week;
    LocalDate date;

    protected Work(){}

    private Work(Member member, Project project, CostType costType,
                 Float gongSoo, Integer year, Integer month, Integer week, LocalDate date){
        this.member=member;
        this.project=project;
        this.costType=costType;
        this.gongSoo=gongSoo;
        this.year=year;
        this.month=month;
        this.week=week;
        this.date=date;
    }

    public static Work of(Member member, Project project, CostType costType,
                  Float gongSoo, Integer year, Integer month, Integer week, LocalDate date){
        return new Work(member, project, costType, gongSoo, year, month, week, date);
    }
}
