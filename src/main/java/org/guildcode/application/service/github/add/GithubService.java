package org.guildcode.application.service.github.add;

import io.smallrye.mutiny.Uni;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.eventbus.EventBus;
import org.guildcode.application.result.ResponseResult;
import org.guildcode.application.result.ResponseResults;
import org.guildcode.application.result.ResponseStatus;
import org.guildcode.application.service.ReactiveService;
import org.guildcode.application.service.github.add.dto.AddGithubUserRequestDto;
import org.guildcode.application.service.github.add.dto.GithubUserDto;
import org.guildcode.application.service.github.add.dto.SimpleUserDto;
import org.guildcode.application.service.github.add.dto.TokenDto;
import org.guildcode.domain.user.User;
import org.guildcode.infrastructure.service.github.GitHubApi;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.validation.ValidationException;
import java.util.Optional;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequestScoped
public class GithubService implements ReactiveService<AddGithubUserRequestDto> {

    private final static Logger LOGGER = Logger.getLogger(GithubService.class.getName());

    @Inject
    Jsonb jsonb;

    @Inject
    GitHubApi gitApi;

    @Inject
    EventBus bus;

    @Inject
    Vertx vertx;

    @Override
    public Uni<ResponseResult> handle(AddGithubUserRequestDto gitUser) {

        return gitApi.getGithubToken(gitUser.getToken())
                .onItem()
                .transform(data -> validateToken.apply(data))
                .onItem().ifNull().failWith(() -> {
                    throw new ValidationException("Was not possible get token user from github");
                })
                .flatMap(token -> getUserInfoFromGithub.apply(token))
                .onItem().ifNull().failWith(() -> {
                    throw new ValidationException("Was not possible get user info from github");
                })
                .map(gitParsed -> this.savoOrUpdate.apply(gitParsed))
                .map(user -> new ResponseResult(ResponseStatus.OK))
                .onFailure()
                .recoverWithItem((error) -> {
                    return ResponseResults.badRequestFromDescriptions(error.getMessage());
                });

    }

    Function<TokenDto, Uni<GithubUserDto>> getUserInfoFromGithub = token -> {
        if (token.getToken() != null) {
            return gitApi.getGithubUserInfo(token.getToken())
                    .onItem()
                    .transform(data -> {
                        System.out.println(data.encodePrettily());
                        return jsonb.fromJson(data.toString(), GithubUserDto.class);
                    });
        }

        return Uni.createFrom().item(new GithubUserDto());
    };

    Function<JsonObject, TokenDto> validateToken = gitToken -> {
        if (gitToken.containsKey("error")) {
            LOGGER.log(Level.INFO, "It's not possible to get data from github, invalid token");
            return null;
        }
        return new TokenDto(gitToken.getString("access_token"));
    };

    Function<GithubUserDto, SimpleUserDto> savoOrUpdate = user -> {
        this.findUser.apply(user)
                .flatMap((userFound) -> {
                    if (userFound == null) {
                        userFound = this.fillUser(user);
                    }
                    return Uni.createFrom().item(userFound);
                })
                .map((userData) -> this.updateUserInfo.apply(userData))
                .map((userUpdated) -> this.generateTokens.apply(userUpdated));
        return null;
    };

    private User fillUser(GithubUserDto user) {

        return null;
    }

    Function<User, User> updateUserInfo = userData -> {

        return null;
    };

    Function<User, TokenDto> generateTokens = user -> {

        return null;
    };

    Function<GithubUserDto, Uni<User>> findUser = gitInfo -> {


        return bus.<User>request("fetch-user-by-email", gitInfo.getEmail())
                .onItem().transform((data) -> {
                    if (data.body() != null) {
                        return data.body();
                    }
                    return null;
                });
    };
}
