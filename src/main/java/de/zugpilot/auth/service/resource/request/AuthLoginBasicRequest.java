package de.zugpilot.auth.service.resource.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AuthLoginBasicRequest {

    @NotBlank
    private String id;
    @NotBlank
    private String password;

}
