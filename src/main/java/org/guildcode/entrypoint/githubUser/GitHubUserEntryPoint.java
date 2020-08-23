package org.guildcode.entrypoint.githubUser;

import io.quarkus.vertx.web.RoutingExchange;

public interface GitHubUserEntryPoint {

    void add(RoutingExchange re);
}
