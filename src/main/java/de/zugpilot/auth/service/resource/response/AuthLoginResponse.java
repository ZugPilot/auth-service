package de.zugpilot.auth.service.resource.response;

import de.zugpilot.auth.service.model.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthLoginResponse {

    private UserDto user;
    private String token;

}
