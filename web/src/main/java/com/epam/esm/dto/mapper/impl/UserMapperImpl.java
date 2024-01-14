package com.epam.esm.dto.mapper.impl;

import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.mapper.Mapper;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements Mapper<User, UserDto> {
    @Override
    public User mapToEntity(UserDto dto) {
        User user = new User();

        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());

        return user;
    }

    @Override
    public UserDto mapToDto(User entity) {
        UserDto userDto = new UserDto();

        userDto.setId(entity.getId());
        userDto.setName(entity.getName());
        userDto.setEmail(entity.getEmail());
        userDto.setPassword(entity.getPassword());
        userDto.setRole(entity.getRole());

        return userDto;
    }
}
