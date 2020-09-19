package org.guildcode.application.service.github.add.dto;


import org.guildcode.domain.user.SimpleUser;
import org.guildcode.domain.user.User;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SimpleUserDto extends SimpleUser {

    public SimpleUserDto(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.avatarUrl = user.getGitInfo().getAvatarUrl();
        this.tags = user.getTags();
    }
}
