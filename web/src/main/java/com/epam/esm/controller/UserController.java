package com.epam.esm.controller;

import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.mapper.impl.UserMapperImpl;
import com.epam.esm.entity.User;
import com.epam.esm.hateoas.impl.UserHateoasAdder;
import com.epam.esm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapperImpl mapper;
    private final UserHateoasAdder hateoasAdder;


    public UserController(UserService userService, UserMapperImpl mapper, UserHateoasAdder hateoasAdder) {
        this.userService = userService;
        this.mapper = mapper;
        this.hateoasAdder = hateoasAdder;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> allUsers(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                  @RequestParam(value = "size", defaultValue = "5", required = false) int size) {
        List<User> users = userService.getAll(page, size);

        return users.stream()
                .map(mapper::mapToDto)
                .peek(hateoasAdder::addLinks)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto userById(@PathVariable long id) {
        User user = userService.getById(id);

        UserDto userDto = mapper.mapToDto(user);
        hateoasAdder.addLinks(userDto);
        return userDto;
    }

}
