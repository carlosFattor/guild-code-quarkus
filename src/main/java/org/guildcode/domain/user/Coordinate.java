package org.guildcode.domain.user;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Collection;

@Data
public class Coordinate {
    private String type;
    private Collection<BigDecimal> coordinates;
}
