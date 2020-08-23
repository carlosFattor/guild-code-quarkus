package org.guildcode.application.service.github.add.dto;


import lombok.Data;
import org.guildcode.domain.user.Names;
import org.guildcode.domain.user.User;

import java.util.Collection;

@Data
public class SimpleUserDto {

    private String email;
    private Names names;
    private String avatarUrl;
    private Collection<String> tags;

    public SimpleUserDto(User user) {
        this.email = user.getEmail();
        this.names = user.getNames();
        this.avatarUrl = user.getAvatarUrl();
        this.tags = user.getTags();
    }
}
