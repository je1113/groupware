package com.infowise.demo.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Table(indexes={
        @Index(columnList="name"),
        @Index(columnList="startDate"),
        @Index(columnList="endDate")
})
@Entity
@ToString(callSuper = true)
public class Project {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long idx;
    @Setter private String name;
    @Setter private LocalDateTime startDate;
    @Setter private LocalDateTime endDate;
    @Setter private Boolean isUse;


    protected Project(){}

    private Project(String name, LocalDateTime startDate,
                    LocalDateTime endDate, Boolean isUse){
        this.name = name;
        this.startDate=startDate;
        this.endDate=endDate;
        this.isUse=isUse;
    }

    public static Project of(String name, LocalDateTime startDate,
                             LocalDateTime endDate, Boolean isUse){
        return new Project(name, startDate, endDate, isUse);
    }
}
