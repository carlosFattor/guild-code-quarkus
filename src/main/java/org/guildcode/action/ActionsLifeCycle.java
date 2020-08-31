package org.guildcode.action;

import io.quarkus.runtime.StartupEvent;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.guildcode.action.user.FindUserAction;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class ActionsLifeCycle {

    @Inject
    Vertx vertx;

    void onStart(@Observes StartupEvent event) {

        var options = new DeploymentOptions();
        options.setInstances(10);

        vertx.deployVerticle(FindUserAction.class, options);
    }
}
