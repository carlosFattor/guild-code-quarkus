package org.guildcode.domain.user.repository;

import org.guildcode.domain.user.User;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;

public interface UserRepository extends ReactivePanacheMongoRepository<User> {
}
