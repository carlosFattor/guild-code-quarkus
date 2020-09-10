package org.guildcode.infrastructure.authentication;

import org.guildcode.security.jwt.JwtVerification;

public interface JwtVerifier {
    JwtVerification verify(String jwt);
}
