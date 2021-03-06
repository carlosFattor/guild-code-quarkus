package org.guildcode.security.jwt;

import io.quarkus.arc.config.ConfigProperties;
import lombok.Data;

@ConfigProperties(
        prefix = "jwt"
)
@Data
public class JwtConfiguration {
    private boolean enabled = true;
    private String secret;
}
