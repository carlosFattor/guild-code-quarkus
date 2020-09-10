package org.guildcode.entrypoint.userInfo;

import io.quarkus.vertx.web.RoutingExchange;

public interface UserInfoEntryPoint {

    void add(RoutingExchange re, String email);
}
