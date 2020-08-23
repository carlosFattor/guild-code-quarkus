package org.guildcode.infrastructure.responses;

import io.quarkus.vertx.web.RoutingExchange;
import org.guildcode.application.result.ResponseResult;

import javax.json.bind.Jsonb;

public class Responses {

    public static void DONE(final RoutingExchange re, final ResponseResult resp, final Jsonb jsonb) {
        re.context().response().setStatusCode(resp.getStatus().toNumber());
        if (resp.getResponse().isPresent()) {
            re.response().end(jsonb.toJson(resp.getResponse()));
        } else {
            re.response().end();
        }
    }

    public static void DONE(final RoutingExchange re, final ResponseResult resp) {
        re.context().response().setStatusCode(resp.getStatus().toNumber());
        if (resp.getResponse().isPresent()) {
            re.response().end();
        } else {
            re.response().end();
        }
    }
}