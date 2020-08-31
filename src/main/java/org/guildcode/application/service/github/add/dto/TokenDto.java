package org.guildcode.application.service.github.add.dto;

import lombok.Data;

@Data
public class TokenDto {
    
    private String token;
    private String type;

    public TokenDto() {
    }

    public TokenDto(String token) {
        this.token = token;
    }
}
