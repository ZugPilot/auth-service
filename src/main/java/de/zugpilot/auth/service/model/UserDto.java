package de.zugpilot.auth.service.model;

import lombok.Data;

@Data
public class UserDto {

    private String id;
    private String name;

    public UserDto(User user){
        this.id = user.getId();
        this.name = user.getName();
    }

}
