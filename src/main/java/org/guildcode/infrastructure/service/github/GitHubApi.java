package org.guildcode.infrastructure.service.github;

import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.WebClient;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class GitHubApi {

    private final String URL_BASE = "/login/oauth/access_token";
    private final String clientId = "d3a25e9930e91076515c";
    private final String clientSecret = "aa0337d0f101032cf1e38b11b7371c23aebb6647";

    @Inject
    Vertx vertx;

    private WebClient clientGithub;

    private WebClient clientGitApi;

    @PostConstruct
    void initialize() {
        this.clientGithub = WebClient.create(vertx,
                new WebClientOptions().setDefaultHost("github.com")
                        .setDefaultPort(443).setSsl(true).setTrustAll(true));

        this.clientGitApi = WebClient.create(vertx,
                new WebClientOptions().setDefaultHost("api.github.com")
                        .setDefaultPort(443).setSsl(true).setTrustAll(true));
    }

    public Uni<JsonObject> getGithubToken(final String githubToken) {
        var url = new StringBuilder()
                .append(URL_BASE)
                .append("?client_id=").append(clientId)
                .append("&client_secret=").append(clientSecret)
                .append("&code=").append(githubToken).toString();

        return clientGithub.get(url)
                .putHeader("accept", "application/json")
                .send()
                .onItem().transform(resp -> {
                    if (resp.statusCode() == 200) {
                        resp.bodyAsJsonObject().getString("");
                        return resp.bodyAsJsonObject();
                    } else {
                        return new JsonObject()
                                .put("code", resp.statusCode())
                                .put("message", resp.bodyAsString());
                    }
                });
    }

    public Uni<JsonObject> getGithubUserInfo(final String githubToken) {
        return clientGitApi.get("/user")
                .putHeader("accept", "application/json")
                .putHeader("Authorization", new StringBuilder().append("Bearer ").append(githubToken).toString())
                .send()
                .onItem().transform(resp -> {
                    if (resp.statusCode() == 200) {
                        return resp.bodyAsJsonObject();
                    } else {
                        return new JsonObject()
                                .put("code", resp.statusCode())
                                .put("message", resp.bodyAsString());
                    }
                });
    }
}
