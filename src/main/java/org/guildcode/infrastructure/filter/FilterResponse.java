package org.guildcode.infrastructure.filter;

import io.quarkus.vertx.web.RouteFilter;
import io.vertx.ext.web.RoutingContext;

public class FilterResponse {

    @RouteFilter(2)
    void filterContentType(RoutingContext rc) {
        rc.response().putHeader("content-type", "application/json; charset=utf-8");
        rc.next();
    }
}
