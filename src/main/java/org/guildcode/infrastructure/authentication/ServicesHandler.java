package org.guildcode.infrastructure.authentication;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import org.guildcode.application.context.ScopedContextStorage;
import org.guildcode.application.result.ResponseResult;
import org.guildcode.application.result.ResponseResults;
import org.guildcode.security.jwt.JwtConfiguration;
import org.guildcode.security.jwt.JwtVerification;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.Jsonb;
import java.io.UnsupportedEncodingException;
import java.time.Clock;
import java.util.Base64;
import java.util.HashMap;
import java.util.Objects;

public class ServicesHandler {

    public static JwtVerification handleAuthentication(JwtConfiguration config, RoutingContext context, ScopedContextStorage storage, Jsonb jsonb) {
        JsonObject claims;
        if (config.isEnabled()) {
            storage.create();
            String authorization = context.request().getHeader("Authorization");
            if (!Objects.isNull(authorization) && !authorization.isEmpty()) {
                if (authorization.toLowerCase().startsWith("bearer ")) {
                    authorization = authorization.substring("bearer ".length());
                } else {
                    createBadCredentialsResponse(context.response(), jsonb, "Malformed 'Authorization' http header value");
                }

                storage.put("__JWT__", authorization);

                try {
                    JwtVerifier verifier = JwtVerifiers.createHmac256(Clock.systemDefaultZone(), config.getSecret());
                    JwtVerification verification = verifier.verify(authorization);
                    storage.put("__JWT_VERIFICATION__", verification);
                    if (verification.hasSucceeded()) {
                        String payload = new String(Base64.getDecoder().decode(verification.getPayload()));
                        claims = jsonb.fromJson(payload, JsonObject.class);
                        storage.put("__JWT_CLAIMS__", claims);
                    }

                    return verification;
                } catch (UnsupportedEncodingException var9) {
                    throw new RuntimeException("Invalid authorization header enconding", var9);
                }
            } else {
                createBadCredentialsResponse(context.response(), jsonb, "Bad Credentials");
                return null;
            }
        } else {
            storage.create();
            JwtVerification verification = new JwtVerification(true, null, null);
            storage.put("__JWT_VERIFICATION__", verification);
            claims = Json.createBuilderFactory(new HashMap<String, String>()).createObjectBuilder()
                    .add("email", "carlos.fattor@gmail.com")
                    .add("nome", "Carlos Fattro").build();
            storage.put("__JWT_CLAIMS__", claims);
            return verification;
        }
    }

    public static void createBadCredentialsResponse(HttpServerResponse response, Jsonb jsonb, String message) {
        response.putHeader("Content-Type", "application/json");
        ResponseResult responseDto = ResponseResults.badRequestFromDescriptions(message);
        response.setStatusCode(responseDto.getStatus().toNumber());
        response.end(jsonb.toJson(responseDto.getResponse()));
    }
}
