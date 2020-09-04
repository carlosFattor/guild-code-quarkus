package org.guildcode.application.service.github.add.dto;

import lombok.Data;
import org.guildcode.application.dto.Dto;

@Data
public class AddGithubUserResponseDto implements Dto {

    private String token;
    private String refreshToken;
    private BasicUserDto user;
}
