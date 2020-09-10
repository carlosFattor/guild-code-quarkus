package org.guildcode.domain.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
public class SimpleUser {

    protected String email;
    protected String name;
    protected String avatarUrl;
    protected Collection<String> tags;

    public SimpleUser(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.avatarUrl = user.getGitInfo().getAvatarUrl();
        this.tags = user.getTags();
    }
}
