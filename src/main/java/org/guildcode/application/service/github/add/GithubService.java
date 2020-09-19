package org.guildcode.application.service.github.add;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Function;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.validation.ValidationException;

import org.guildcode.application.result.ResponseResult;
import org.guildcode.application.result.ResponseResults;
import org.guildcode.application.result.ResponseStatus;
import org.guildcode.application.service.ReactiveService;
import org.guildcode.application.service.github.add.dto.AddGithubUserRequestDto;
import org.guildcode.application.service.github.add.dto.AddGithubUserResponseDto;
import org.guildcode.application.service.github.add.dto.BasicUserDto;
import org.guildcode.application.service.github.add.dto.GithubUserDto;
import org.guildcode.application.service.github.add.dto.TokenDto;
import org.guildcode.domain.user.User;
import org.guildcode.domain.user.repository.UserRepository;
import org.guildcode.infrastructure.service.github.GitHubApi;
import org.guildcode.security.jwt.JwtCreateToken;
import org.jboss.logging.Logger;

import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.eventbus.EventBus;

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
    UserRepository userRepository;

    @Inject
    JwtCreateToken jwtCreator;

    @Override
    public Uni<ResponseResult> handle(AddGithubUserRequestDto gitUser) {

        return gitApi.getGithubToken(gitUser.getToken())
                .onItem()
                .transform(validateToken)
                .onItem().ifNull().failWith(() -> {
                    throw new ValidationException("It was not possible get token user from github");
                })
                .flatMap(getUserInfoFromGithub::apply)
                .onItem().ifNull().failWith(() -> {
                    throw new ValidationException("It was not possible get user info from github");
                })
                .flatMap(findUser::apply)
                .onItem().ifNull().failWith(() -> {
                    throw new ValidationException("it was not found an user");
                })
                .flatMap(updateUserInfo::apply)
                .map(generateTokens)
                .map(toResponse)
                .onFailure()
                .recoverWithItem((error) -> {
                    LOGGER.error(error.getMessage(), error);
                    return ResponseResults.badRequestFromDescriptions(error.getMessage());
                });
    }

    Function<AddGithubUserResponseDto, ResponseResult> toResponse = resp -> {
        return new ResponseResult(ResponseStatus.OK, resp);
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
        var basicUser = new BasicUserDto(user);
        var timeToken = LocalDateTime.now().plusMinutes(5);
        var timeTokenRefresh = LocalDateTime.now().plusHours(10);
        var token = this.jwtCreator.generate(timeToken, basicUser);
        var refreshToken = this.jwtCreator.generate(timeToken, basicUser);
        resp.setUser(basicUser);
        resp.setRefreshToken(refreshToken);
        resp.setToken(token);

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
