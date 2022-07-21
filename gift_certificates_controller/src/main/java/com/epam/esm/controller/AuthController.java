package com.epam.esm.controller;

import com.epam.esm.dto.UserDto;
import com.epam.esm.security.jwt.JwtProvider;
import com.epam.esm.security.model.AuthRequest;
import com.epam.esm.security.model.AuthResponse;
import com.epam.esm.service.UserService;
import io.jsonwebtoken.impl.DefaultClaims;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
  private final AuthenticationManager authenticationManager;
  private final UserService userService;
  private final JwtProvider jwtProvider;

  @Autowired
  public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtProvider jwtProvider) {
    this.authenticationManager = authenticationManager;
    this.userService = userService;
    this.jwtProvider = jwtProvider;
  }

  @PostMapping("/register")
  public ResponseEntity<Void> registerUser(@RequestBody UserDto userDto) {
    userService.saveUser(userDto);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/auth")
  public ResponseEntity<AuthResponse> auth(@RequestBody AuthRequest authRequest) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        authRequest.getLogin(), authRequest.getPassword()));
    String token = jwtProvider.generateToken(
            userService.loadUserByUsername(authRequest.getLogin())
        );
    return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
  }

  @PostMapping("/refresh")
  public ResponseEntity<AuthResponse> refreshToken(HttpServletRequest request){
    DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");
    String token = null;
    if(Objects.nonNull(claims)){
      Map<String, Object> expectedMap = new HashMap<>(claims);
      token = jwtProvider.generateRefreshedToken(expectedMap, expectedMap.get("sub").toString());
    }
    return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
  }
}
