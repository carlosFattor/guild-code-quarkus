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
    private String clientId = "7c88c94314b715f98752";
    private String clientSecret = "23d2d25acc0e48d47fe40aca3c20c99df0c703e9";

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
        var token = this.sanitizeToken(githubToken);
        return clientGitApi.get("/user")
                .putHeader("accept", "application/json")
                .putHeader("Authorization", new StringBuilder().append("token ").append(token).toString())
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

    private String sanitizeToken(String githubToken) {
        var token = githubToken.split("=")[1];
        return token.split("&")[0];
    }
}
