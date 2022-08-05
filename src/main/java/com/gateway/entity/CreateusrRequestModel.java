package com.gateway.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateusrRequestModel {


    private String firstName;


    private String lastName;


    private String email;


    private String password;

}
