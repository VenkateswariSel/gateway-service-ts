package com.gateway.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Privilege {

    @Id
    @Column(name="ID")
    private Integer id;

    @Column(name="PRIVILEGENAME")
    private String privilegeName;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

}