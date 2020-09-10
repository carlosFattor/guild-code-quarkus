package org.guildcode.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import io.quarkus.arc.config.ConfigProperties;
import lombok.Data;
import org.guildcode.application.service.github.add.dto.BasicUserDto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@ConfigProperties(
        prefix = "quarkus.jwt"
)
@Data
public class JwtCreateToken {
    Boolean enabled;
    String secret;

    public String generate(LocalDateTime expireAt,  BasicUserDto user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withClaim("email", user.getEmail())
                    .withClaim("roles", user.getRoles().toString())
                    .withExpiresAt(Date.from(expireAt.atZone(ZoneId.systemDefault()).toInstant()))
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("It was not create jwt tokem", exception);
        }
    }
}
