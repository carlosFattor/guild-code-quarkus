package org.guildcode.application.action.token;

import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.eventbus.Message;

import javax.enterprise.context.ApplicationScoped;
import javax.xml.bind.ValidationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@ApplicationScoped
public class Create {

//    @Inject
//    JwtCreateToken generator;

    @ConsumeEvent(value = "create-token")
    private Uni<String> handleCreationToken(Message<JsonObject> msg) {
        System.out.println("####################TESTE###############");
        try {
            var body = msg.body();
            var expireAt = new SimpleDateFormat("dd/MM/yyyy")
                    .parse(body.getString("expiredAt"));
//            var token = generator.generate(expireAt, body.getString("secret"), body.getJsonObject("user"));
            return Uni.createFrom().item("");
        } catch (ParseException e) {
            return Uni.createFrom().failure(new ValidationException("Error trying to create JWT", e));
        }
    }
}
