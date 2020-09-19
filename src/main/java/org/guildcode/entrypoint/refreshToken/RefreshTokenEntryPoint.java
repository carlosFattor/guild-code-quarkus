package org.guildcode.entrypoint.refreshToken;

import io.quarkus.vertx.web.RoutingExchange;
import org.guildcode.application.service.authentication.refreshToken.dto.RefreshTokenRequestDto;

public interface RefreshTokenEntryPoint {

    void refresh(RoutingExchange re, RefreshTokenRequestDto token);
}
