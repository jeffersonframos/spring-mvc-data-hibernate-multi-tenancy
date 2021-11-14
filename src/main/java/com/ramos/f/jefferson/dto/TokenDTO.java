package com.ramos.f.jefferson.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenDTO {
    private final String token;
    
    @JsonProperty("user")
    private final UserDTO userDTO;

    public TokenDTO(String token, UserDTO userDTO) {
        this.token = token;
        this.userDTO = userDTO;
    }

    public String getToken() {
        return token;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }
}
