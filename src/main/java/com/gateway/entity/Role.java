package com.gateway.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @Column(name="roleid")
    private Integer roleId;

    @Column(name="rolename")
    private String roleName;

    @ManyToMany(mappedBy="roles")
    private List<Userentity> users;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="roles_authorities",
            joinColumns = @JoinColumn(name="role_id",referencedColumnName="roleid"),
            inverseJoinColumns=@JoinColumn(name="privilege_id",referencedColumnName="id"))
    private List<Privilege> privileges;

}