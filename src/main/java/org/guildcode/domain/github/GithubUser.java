package org.guildcode.domain.github;

import lombok.Data;

@Data
public class GithubUser {

    private String avatar_url;
    private String bio;
    private String email;
    private Integer followers;
    private Integer following;
}
