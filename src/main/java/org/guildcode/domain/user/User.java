package org.guildcode.domain.user;


import io.quarkus.mongodb.panache.MongoEntity;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonId;
import org.guildcode.domain.enums.Role;

import java.util.Collection;

@Data
@MongoEntity(collection = "user")
public class User {

    @BsonId
    private String id;
    private String email;
    private String password;
    private String name;
    private Collection<String> tags;
    private Location location;
    private Collection<Role> roles;
    private GithubInfo gitInfo;

}
