package org.guildcode.entrypoint.refreshToken.rest;

import io.quarkus.vertx.web.Body;
import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RoutingExchange;
import io.vertx.core.http.HttpMethod;
import org.guildcode.application.context.ScopedContextStorage;
import org.guildcode.application.service.authentication.refreshToken.RefreshTokenService;
import org.guildcode.application.service.authentication.refreshToken.dto.RefreshTokenRequestDto;
import org.guildcode.entrypoint.refreshToken.RefreshTokenEntryPoint;
import org.guildcode.infrastructure.responses.Responses;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.ws.rs.core.MediaType;

@RequestScoped
public class RefreshTokenRestEntryPoint implements RefreshTokenEntryPoint {

    @Inject
    ScopedContextStorage storage;

    @Inject
    RefreshTokenService tokenService;

    @Inject
    Jsonb jsonb;

    @Override
    @Route(path = "/users/refresh/token", methods = HttpMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    public void refresh(RoutingExchange re, @Body RefreshTokenRequestDto token) {

        tokenService.handle(token).subscribe().with(resp -> {
            Responses.DONE(re, resp, jsonb);
        }, error -> {
            re.response().end(error.getLocalizedMessage());
        });
    }
}
