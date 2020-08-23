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
    private Names names;
    private String avatarUrl;
    private String biography;
    private String blog;
    private String gitIg;
    private Integer publicRepos;
    private String reposUrl;
    private Collection<String> tags;
    private Location location;
    private Collection<Role> roles;

}
