package org.guildcode.domain.user;


import io.quarkus.mongodb.panache.MongoEntity;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.guildcode.application.service.github.add.dto.GithubUserDto;
import org.guildcode.domain.enums.Role;

import java.util.Collection;

@Data
@MongoEntity(collection = "user")
public class User {

    @BsonId
    private ObjectId id;
    private String email;
    private String password;
    private String name;
    private Collection<String> tags;
    private Location location;
    private Collection<Role> roles;
    private GithubInfo gitInfo;

    public User() {
    }

    public User(GithubUserDto userGit) {
        this.gitInfo = new GithubInfo(userGit);
        this.email = userGit.getEmail();
        this.name = userGit.getName();
    }
}
