package org.guildcode.application.service.github.add;

import io.smallrye.mutiny.Uni;
import org.guildcode.application.result.ResponseResult;
import org.guildcode.application.result.ResponseStatus;
import org.guildcode.application.service.ReactiveService;
import org.guildcode.application.service.github.add.dto.AddGithubUserRequestDto;
import org.guildcode.application.service.github.add.dto.SimpleUserDto;
import org.guildcode.application.service.github.add.dto.TokenDto;
import org.guildcode.domain.github.GithubUser;
import org.guildcode.infrastructure.service.github.GitHubApi;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.json.bind.Jsonb;
import java.util.function.Function;

@RequestScoped
public class GithubService implements ReactiveService<AddGithubUserRequestDto> {

    @Inject
    Jsonb jsonb;

    @Inject
    GitHubApi gitApi;

//    @Inject
//    UserRepository userRepository;

    @Override
    public Uni<ResponseResult> handle(AddGithubUserRequestDto gitUser) {

        return gitApi.getGithubToken(gitUser.getToken())
                .onItem()
                .transform(gitToken -> new TokenDto())
                .flatMap(token -> gitApi.getGithubUserInfo(token.getToken()))
                .map(gitData -> this.parseGitData)
                .map(gitParsed -> this.savoOrUpdate)
                .map(user -> {

                    return new ResponseResult(ResponseStatus.OK);
                });
    }

    Function<GithubUser, SimpleUserDto> savoOrUpdate = user -> {

        return null;
    };

    Function<JsonObject, GithubUser> parseGitData = data -> {
        var gitUserData = jsonb.fromJson(data.toString(), GithubUser.class);

        return gitUserData;
    };

    Function<JsonObject, TokenDto> getToken = json -> {
        var token = new TokenDto();
        return token;
    };
}
