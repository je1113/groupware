package com.infowise.demo.Entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "role")
@Getter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Role(){}

    public Role(String name){
        super();
        this.name=name;
    }

}
