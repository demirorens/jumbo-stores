package com.jumbo.store.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank
    private String emailOrUsername;

    @NotBlank
    private String password;

}
