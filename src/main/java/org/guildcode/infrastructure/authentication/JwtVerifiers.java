package org.guildcode.infrastructure.authentication;

import org.guildcode.security.jwt.Hmac256JwtVerifier;

import java.io.UnsupportedEncodingException;
import java.time.Clock;
import java.util.Objects;

public class JwtVerifiers {

    public static JwtVerifier createHmac256(Clock clock, String plainSecret) throws UnsupportedEncodingException {
        Objects.requireNonNull(clock);
        Objects.requireNonNull(plainSecret);
        return new Hmac256JwtVerifier(clock, plainSecret);
    }
}
