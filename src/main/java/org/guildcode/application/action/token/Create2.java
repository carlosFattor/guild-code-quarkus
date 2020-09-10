package org.guildcode.application.action.token;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.vertx.core.AbstractVerticle;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Create2 extends AbstractVerticle {

    @ConfigProperty(name = "address") String address;

    @Override
    public Uni<Void> asyncStart() {
        System.out.println("####################TESTE" + address);
        return vertx.eventBus().consumer(address)
                .handler(m -> {
                    System.out.println(m.body());
                    m.replyAndForget("hello");
                })
                .completionHandler();
    }
}
