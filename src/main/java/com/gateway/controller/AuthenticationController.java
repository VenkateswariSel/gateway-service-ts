package com.gateway.controller;

import com.gateway.config.JWTutil;
import com.gateway.entity.AuthenticationRequest;
import com.gateway.entity.AuthenticationResponse;
import com.gateway.entity.CreateUserResponseModel;
import com.gateway.entity.CreateusrRequestModel;
import com.gateway.service.EStockUserdetails;
import com.gateway.service.UserRegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1.0/gateway/market")
@Slf4j
@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JWTutil jwtTokenUtil;

    private final EStockUserdetails userDetailsService;

    private final UserRegistrationService userRegistrationService;



    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JWTutil jwtTokenUtil,
                                    EStockUserdetails userDetailsService, UserRegistrationService userRegistrationService) {
        super();
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.userRegistrationService  = userRegistrationService;

    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authrequest) throws Exception {
        log.info("Received request for authentication");
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authrequest.getUserEmail(), authrequest.getPassword()));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authrequest.getUserEmail());
        AuthenticationResponse authresponse = new AuthenticationResponse(jwtTokenUtil.generateToken(userDetails));

        return ResponseEntity.ok(authresponse);
    }

    @PostMapping("/register")
    public ResponseEntity<CreateUserResponseModel> createUser(@Validated @RequestBody CreateusrRequestModel userDetails)
    {
        System.out.println("Request came");
        CreateUserResponseModel userResponseModel = userRegistrationService.creatuser(userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseModel);


    }

}