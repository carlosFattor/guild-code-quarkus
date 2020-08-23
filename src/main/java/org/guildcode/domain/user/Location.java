package org.guildcode.domain.user;

import lombok.Data;

@Data
public class Location {

    private Address address;
    private Coordinate coordinate;
}
