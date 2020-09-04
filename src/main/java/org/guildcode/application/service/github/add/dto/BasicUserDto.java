package org.guildcode.application.service.github.add.dto;

import lombok.Data;
import org.guildcode.domain.user.User;

import java.util.Collection;

@Data
public class BasicUserDto {

    private String email;
    private String avatarUrl;
    private Collection<String> tags;
    private String bio;
    private String blog;

    public BasicUserDto(final User user) {
        this.email = user.getEmail();
        this.avatarUrl = user.getGitInfo().getAvatarUrl();
        this.tags = user.getTags();
        this.bio = user.getGitInfo().getBio();
        this.blog = user.getGitInfo().getBlog();
    }
}
