package org.guildcode.action.user;

import io.smallrye.mutiny.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import org.guildcode.domain.user.User;
import org.guildcode.domain.user.repository.UserRepository;

import javax.inject.Inject;
import java.util.function.Function;

public class FindUserAction extends AbstractVerticle {

    @Inject
    UserRepository userRepository;

    @Override
    public void init(Vertx vertx, Context context) {
        super.init(vertx, context);

        vertx.eventBus().<String>consumer("fetch-user-by-email", msg -> {

            System.out.println(msg.body());
            var query = "{email: ?1}";

            userRepository.find(query, msg.body())
                    .firstResult()
                    .onItem()
                    .transform(toResponse)
                    .subscribe().with((user) -> {
                msg.reply(user);
            });
        });
    }

    Function<User, User> toResponse = user -> {
        return user;
    };
}
