package com.epam.esm.dto;

import com.epam.esm.entity.Role;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class UserDto extends RepresentationModel<UserDto> {

    private long id;
    private String email;
    private String password;
    private Role role;
    private String name;
}
