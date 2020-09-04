package org.guildcode.domain.user.repository;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepositoryBase;
import org.guildcode.domain.user.User;

public interface UserRepository extends ReactivePanacheMongoRepository<User> {
}
