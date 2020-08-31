package org.guildcode.application.service.github.add.dto;

import lombok.Data;

@Data
public class GithubUserDto {

    private String id;
    private String name;
    private String avatar_url;
    private String email;
    private String url;
    private String bio;
    private String blog;
    private Integer public_repos;
    private String repos_url;
}
