package org.guildcode.application.service.authentication.refreshToken.dto;

import lombok.Data;
import org.guildcode.application.dto.Dto;
import org.guildcode.application.service.github.add.dto.BasicUserDto;

@Data
public class RefreshTokenResponseDto implements Dto {

    private String token;
    private String refreshToken;
    private BasicUserDto user;
}
