package org.guildcode.application.service.github.add;

import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import org.guildcode.application.result.ResponseResult;
import org.guildcode.application.result.ResponseResults;
import org.guildcode.application.result.ResponseStatus;
import org.guildcode.application.service.ReactiveService;
import org.guildcode.application.service.github.add.dto.*;
import org.guildcode.domain.user.User;
import org.guildcode.domain.user.repository.UserRepository;
import org.guildcode.infrastructure.service.github.GitHubApi;
import org.jboss.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.validation.ValidationException;
import java.util.Objects;
import java.util.function.Function;

@RequestScoped
public class GithubService implements ReactiveService<AddGithubUserRequestDto> {

    private final static Logger LOGGER = Logger.getLogger(GithubService.class.getName());

    @Inject
    Jsonb jsonb;

    @Inject
    GitHubApi gitApi;

    @Inject
    UserRepository userRepository;

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
                .flatMap(gitParsed -> this.findUser.apply(gitParsed))
                .onItem().ifNull().failWith(() -> {
                    throw new ValidationException("Was not found an user");
                })
                .flatMap(userParsed -> this.updateUserInfo.apply(userParsed))
                .map(userData -> this.generateTokens.apply(userData))
                .map(resp -> toResponse.apply(resp))
                .onFailure()
                .recoverWithItem((error) -> {
                    LOGGER.error(error.getMessage(), error);
                    return ResponseResults.badRequestFromDescriptions(error.getMessage());
                });
    }

    Function<AddGithubUserResponseDto, ResponseResult> toResponse = resp -> {
        return new ResponseResult(ResponseStatus.CREATED, resp);
    };

    Function<TokenDto, Uni<GithubUserDto>> getUserInfoFromGithub = token -> {
        if (token.getToken() != null) {
            return gitApi.getGithubUserInfo(token.getToken())
                    .onItem()
                    .transform(data -> jsonb.fromJson(data.toString(), GithubUserDto.class));
        }

        return Uni.createFrom().item(new GithubUserDto());
    };

    Function<JsonObject, TokenDto> validateToken = gitToken -> {
        if (gitToken.containsKey("error")) {
            LOGGER.error("It's not possible to get data from github, invalid token");
            return null;
        }
        return new TokenDto(gitToken.getString("access_token"));
    };

    Function<User, Uni<User>> updateUserInfo = userData -> {
        return userRepository.persistOrUpdate(userData)
                .onItem()
                .transform((data) -> userData);
    };

    Function<User, AddGithubUserResponseDto> generateTokens = user -> {
        var resp = new AddGithubUserResponseDto();
        resp.setUser(new BasicUserDto(user));
        return resp;
    };

    Function<GithubUserDto, Uni<User>> findUser = gitInfo -> {
        var query = "{email: ?1}";
        return userRepository.find(query, gitInfo.getEmail())
                .firstResult()
                .onItem()
                .transform(userGit -> Objects.requireNonNullElseGet(userGit, () -> new User(gitInfo)));
    };
}
