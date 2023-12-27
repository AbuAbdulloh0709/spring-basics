package com.epam.esm.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class UserDto extends RepresentationModel<UserDto> {

    private long id;
    private String name;
}
