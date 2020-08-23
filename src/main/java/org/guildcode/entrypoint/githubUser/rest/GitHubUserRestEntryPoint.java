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
import javax.ws.rs.core.MediaType;

@RequestScoped
@RouteBase(path = "api/v1", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
public class GitHubUserRestEntryPoint implements GitHubUserEntryPoint {

    @Inject
    Jsonb jsonb;

    @Inject
    GithubService githubServise;

    @Override
    @Route(path = "/users/github/:token", methods = HttpMethod.GET)
    public void add(RoutingExchange re) {
        System.out.println("Teste#################");
        var gitTokenDto = new AddGithubUserRequestDto();
        gitTokenDto.setToken(re.context().pathParam("token"));

        githubServise.handle(gitTokenDto).subscribe().with(resp -> {
            Responses.DONE(re, resp, jsonb);
        }, error -> {
            re.response().end(error.getLocalizedMessage());
        });
    }
}
