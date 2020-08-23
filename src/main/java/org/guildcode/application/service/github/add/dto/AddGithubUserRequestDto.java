package org.guildcode.application.service.github.add.dto;

import lombok.Data;
import org.guildcode.application.dto.Dto;

@Data
public class AddGithubUserRequestDto implements Dto {
    private String token;
}
