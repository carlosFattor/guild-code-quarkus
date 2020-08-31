package org.guildcode.domain.user;

import lombok.Data;

@Data
public class GithubInfo {

    private String id;
    private String name;
    private String avatarUrl;
    private String email;
    private String url;
    private String bio;
    private String blog;
    private Integer publicRepos;
    private String reposUrl;
}
