package org.guildcode.action.user;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import org.guildcode.domain.user.User;

import java.util.function.Function;

public class FindUserAction extends AbstractVerticle {

    @Override
    public void init(Vertx vertx, Context context) {
        super.init(vertx, context);

        vertx.eventBus().<String>consumer("fetch-user-by-email", msg -> {
            System.out.println(msg);
            msg.reply(msg.body().toUpperCase());
        });
    }

    Function<User, User> toResponse = user -> {
        System.out.println(user.toString());
        return user;
    };
}
