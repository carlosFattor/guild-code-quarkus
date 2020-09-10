package org.guildcode.entrypoint.userInfo.rest;

import io.quarkus.vertx.web.Param;
import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RouteBase;
import io.quarkus.vertx.web.RoutingExchange;
import io.vertx.core.http.HttpMethod;
import org.guildcode.application.context.ScopedContextStorage;
import org.guildcode.application.service.user.info.UserInfoService;
import org.guildcode.entrypoint.userInfo.UserInfoEntryPoint;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RouteBase(path = "api/v1")
@RequestScoped
public class UserInfoRestEntrypoint implements UserInfoEntryPoint {

    @Inject
    UserInfoService userService;

    @Inject
    ScopedContextStorage storage;

    @Override
    @Route(path = "/users/info/:email", methods = HttpMethod.GET)
    public void add(RoutingExchange re, @Param String email) {
        System.out.println(email);
        System.out.println(storage.get("__JWT_CLAIMS__"));
        re.ok().end();
    }
}
