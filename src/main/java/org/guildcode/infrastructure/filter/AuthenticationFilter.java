package org.guildcode.infrastructure.filter;

import io.quarkus.vertx.web.RouteFilter;
import io.vertx.ext.web.RoutingContext;
import org.guildcode.application.context.ScopedContextStorage;
import org.guildcode.application.result.ResponseStatus;
import org.guildcode.infrastructure.authentication.ServicesHandler;
import org.guildcode.security.jwt.JwtConfiguration;

import javax.inject.Inject;
import javax.json.bind.Jsonb;

import static java.util.Objects.isNull;

public class AuthenticationFilter {

    @Inject
    JwtConfiguration jwtConfiguration;

    @Inject
    Jsonb jsonb;

    @Inject
    ScopedContextStorage storage;

    @RouteFilter(1)
    void filterAuthorization(RoutingContext rc) {
        
        final var authentication = ServicesHandler.handleAuthentication(jwtConfiguration, rc, storage, jsonb);
        
        if (rc.request().uri().contains("api")) {

            if (isNull(authentication) || !authentication.hasSucceeded()) {
                rc.response().setStatusCode(ResponseStatus.UNAUTHORIZED.toNumber());
                rc.response().end();
                return;
            }
            rc.next();
        } else {
            rc.next();
        }

    }
}
