package org.guildcode.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtVerification {

    private final boolean hasSucceededField;
    private final String header;
    private final String payload;

    public boolean hasSucceeded() {
        return this.hasSucceededField;
    }

    public String getHeader() {
        return this.header;
    }

    public String getPayload() {
        return this.payload;
    }
}
