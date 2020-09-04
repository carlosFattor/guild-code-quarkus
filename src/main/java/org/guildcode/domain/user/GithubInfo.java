package org.guildcode.domain.user;

import lombok.Data;
import org.guildcode.application.service.github.add.dto.GithubUserDto;

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

    public GithubInfo() {
    }

    public GithubInfo(final GithubUserDto userGit) {
        this.id = userGit.getId();
        this.name = userGit.getName();
        this.avatarUrl = userGit.getAvatar_url();
        this.email = userGit.getEmail();
        this.url = userGit.getUrl();
        this.bio = userGit.getBio();
        this.blog = userGit.getBlog();
        this.publicRepos = userGit.getPublic_repos();
        this.reposUrl = userGit.getRepos_url();
    }
}
