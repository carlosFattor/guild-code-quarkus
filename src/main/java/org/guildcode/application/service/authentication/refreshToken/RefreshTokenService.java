package org.guildcode.application.service.authentication.refreshToken;

import io.smallrye.mutiny.Uni;

import org.guildcode.application.context.ScopedContextStorage;
import org.guildcode.application.result.ResponseResult;
import org.guildcode.application.service.ReactiveService;
import org.guildcode.application.service.ServiceBase;
import org.guildcode.application.service.authentication.refreshToken.dto.RefreshTokenRequestDto;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class RefreshTokenService extends ServiceBase implements ReactiveService<RefreshTokenRequestDto> {

    @Inject
    ScopedContextStorage storage;

    @Override
    public Uni<ResponseResult> handle(RefreshTokenRequestDto request) {

        return null;
    }
}
