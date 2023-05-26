package com.infowise.demo.Entity;

import com.infowise.demo.Enum.CostType;
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
    @Setter private CostType costType;


    protected Project(){}

    private Project(String name, LocalDateTime startDate,
                    LocalDateTime endDate, Boolean isUse, CostType costType){
        this.name = name;
        this.startDate=startDate;
        this.endDate=endDate;
        this.isUse=isUse;
        this.costType = costType;
    }

    public static Project of(String name, LocalDateTime startDate,
                             LocalDateTime endDate, Boolean isUse,CostType costType){
        return new Project(name, startDate, endDate, isUse,costType);
    }
}
