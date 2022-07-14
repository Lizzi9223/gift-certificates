package com.epam.esm.controller;

import com.epam.esm.dto.UserDto;
import com.epam.esm.security.jwt.JwtProvider;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
  private final UserService userService;
  private final JwtProvider jwtProvider;

  @Autowired
  public AuthController(UserService userService, JwtProvider jwtProvider) {
    this.userService = userService;
    this.jwtProvider = jwtProvider;
  }

  @PostMapping("/register")
  public ResponseEntity<Void> registerUser(@RequestBody UserDto userDto) {
    userService.saveUser(userDto);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/auth")
  public String auth(@RequestBody UserDto authUser) {
    UserDto userDto = userService.findByLoginAndPassword(authUser);
    return jwtProvider.generateToken(userDto.getLogin());
  }
}
