package com.epam.esm.controller;

import com.epam.esm.dto.UserCredentialDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.mapper.impl.UserMapperImpl;
import com.epam.esm.entity.User;
import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.jwt.JWTProvider;
import com.epam.esm.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;
    private final HateoasAdder<UserDto> hateoasAdder;
    private final UserMapperImpl userMapper;

    public AuthenticationController(UserService userService, AuthenticationManager authenticationManager, JWTProvider jwtProvider, HateoasAdder<UserDto> hateoasAdder, UserMapperImpl userMapper) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.hateoasAdder = hateoasAdder;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public UserDto registerUser(@RequestBody UserDto user) {
        User addedUser = userService.insert(userMapper.mapToEntity(user));

        UserDto userDto = userMapper.mapToDto(addedUser);
        hateoasAdder.addLinks(userDto);
        return userDto;
    }

    @PostMapping("/auth")
    @ResponseStatus(HttpStatus.OK)
    public UserCredentialDto authorizeUser(@RequestBody UserCredentialDto userCredentialDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userCredentialDto.getEmail(), userCredentialDto.getPassword()));
        String token = jwtProvider.generateToken(userCredentialDto.getEmail());
        userCredentialDto.setToken(token);
        return userCredentialDto;
    }
}
