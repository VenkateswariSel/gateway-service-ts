package com.gateway.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class AuthenticationResponse {

    private String jwt;

}