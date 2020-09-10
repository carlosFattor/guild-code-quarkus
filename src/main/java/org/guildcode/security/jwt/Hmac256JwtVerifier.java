package org.guildcode.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWTVerifier.BaseVerification;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.guildcode.infrastructure.authentication.JwtVerifier;

import java.io.UnsupportedEncodingException;
import java.time.Clock;
import java.util.Date;
import java.util.Objects;

public class Hmac256JwtVerifier implements JwtVerifier {

    private final JWTVerifier jwtVerifier;

    public Hmac256JwtVerifier(Clock clock, String plainSecret) {
        Objects.requireNonNull(clock);
        Objects.requireNonNull(plainSecret);
        BaseVerification verification = (BaseVerification) JWT.require(Algorithm.HMAC256(plainSecret));
        this.jwtVerifier = verification.build(() -> Date.from(clock.instant()));
    }

    public JwtVerification verify(String token) {
        Objects.requireNonNull(token);

        try {
            DecodedJWT parsedToken = this.jwtVerifier.verify(token);
            return new JwtVerification(true, parsedToken.getHeader(), parsedToken.getPayload());
        } catch (JWTVerificationException var3) {
            return new JwtVerification(false, null, null);
        }
    }
}
