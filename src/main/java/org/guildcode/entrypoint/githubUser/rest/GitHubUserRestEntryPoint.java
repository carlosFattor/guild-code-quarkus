package org.guildcode.entrypoint.githubUser.rest;

import io.quarkus.vertx.web.*;
import io.vertx.core.http.HttpMethod;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.guildcode.application.service.github.add.GithubService;
import org.guildcode.application.service.github.add.dto.AddGithubUserRequestDto;
import org.guildcode.entrypoint.githubUser.GitHubUserEntryPoint;
import org.guildcode.infrastructure.responses.Responses;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;

@RequestScoped
public class GitHubUserRestEntryPoint implements GitHubUserEntryPoint {

    @ConfigProperty(name = "git.URL_GITHUB_REDIRECT")
    String URL_GITHUB_REDIRECT;

    @ConfigProperty(name = "git.clientId")
    String clientId;

    @Inject
    Jsonb jsonb;

    @Inject
    GithubService githubService;

    @Override
    @Route(path = "/users/github", methods = HttpMethod.GET)
    public void add(RoutingExchange re, @Param String code) {

        var gitTokenDto = new AddGithubUserRequestDto();
        gitTokenDto.setToken(code);

        githubService.handle(gitTokenDto).subscribe().with(resp -> {
            Responses.DONE(re, resp, jsonb);
        }, error -> {
            re.response().end(error.getLocalizedMessage());
        });
    }

    @Route(path = "/users/github/login/redirect")
    public void redirect(RoutingExchange re) {
        var url = new StringBuilder()
                .append(URL_GITHUB_REDIRECT)
                .append("?client_id=")
                .append(clientId).toString();
        re.response().putHeader("location", url);
        re.response().setStatusCode(302);
        re.response().end();
    }
}
