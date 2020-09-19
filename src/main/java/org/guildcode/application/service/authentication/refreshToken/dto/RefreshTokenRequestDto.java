package org.guildcode.application.service.authentication.refreshToken.dto;

import lombok.Data;
import org.guildcode.application.dto.Dto;

@Data
public class RefreshTokenRequestDto implements Dto {
    private String refreshToken;
}
