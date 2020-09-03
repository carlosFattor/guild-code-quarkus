package org.guildcode.entrypoint.githubUser.rest;

import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RouteBase;
import io.quarkus.vertx.web.RoutingExchange;
import io.vertx.core.http.HttpMethod;
import org.guildcode.application.service.github.add.GithubService;
import org.guildcode.application.service.github.add.dto.AddGithubUserRequestDto;
import org.guildcode.entrypoint.githubUser.GitHubUserEntryPoint;
import org.guildcode.infrastructure.responses.Responses;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;

@RequestScoped
@RouteBase(path = "api/v1")
public class GitHubUserRestEntryPoint implements GitHubUserEntryPoint {

    private final String URL_REDIRECT = "https://github.com/login/oauth/authorize";
    private final String clientId = "d3a25e9930e91076515c";

    @Inject
    Jsonb jsonb;

    @Inject
    GithubService githubService;

    @Override
    @Route(path = "/users/github", methods = HttpMethod.GET)
    public void add(RoutingExchange re) {

        var gitTokenDto = new AddGithubUserRequestDto();
        var queryParams = re.context().queryParam("code");
        gitTokenDto.setToken(queryParams.get(0));

        githubService.handle(gitTokenDto).subscribe().with(resp -> {
            Responses.DONE(re, resp, jsonb);
        }, error -> {
            re.response().end(error.getLocalizedMessage());
        });
    }

    @Route(path = "/users/github/login/redirect")
    public void redirect(RoutingExchange re) {
        var url = new StringBuilder()
                .append(URL_REDIRECT)
                .append("?client_id=").append(clientId).toString();
        re.response().putHeader("location", url);
        re.response().setStatusCode(302);
        re.response().end();
    }
}
