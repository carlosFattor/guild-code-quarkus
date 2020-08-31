package org.guildcode.application.service.github.add.dto;


import lombok.Data;
import org.guildcode.domain.user.User;

import java.util.Collection;

@Data
public class SimpleUserDto {

    private String email;
    private String name;
    private String avatarUrl;
    private Collection<String> tags;

    public SimpleUserDto(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.avatarUrl = user.getGitInfo().getAvatarUrl();
        this.tags = user.getTags();
    }
}
