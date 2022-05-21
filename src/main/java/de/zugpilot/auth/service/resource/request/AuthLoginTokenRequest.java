package de.zugpilot.auth.service.resource.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AuthLoginTokenRequest {

    @NotBlank
    private String token;

}
