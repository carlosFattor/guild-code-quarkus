package org.guildcode.application.action;

import io.quarkus.runtime.StartupEvent;
import io.vertx.core.DeploymentOptions;
import io.vertx.mutiny.core.Vertx;
import org.guildcode.application.action.token.Create2;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class VerticleDeployer {

    public void init(@Observes StartupEvent e, Vertx vertx, Create2 verticle) {
        var options = new DeploymentOptions();
        options.setInstances(10);
        vertx.deployVerticle(verticle).await().indefinitely();
    }
}
