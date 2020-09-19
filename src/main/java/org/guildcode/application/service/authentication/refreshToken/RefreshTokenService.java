package org.guildcode.application.service.authentication;

import io.smallrye.mutiny.Uni;
import org.guildcode.application.result.ResponseResult;
import org.guildcode.application.service.ReactiveService;
import org.guildcode.application.service.ServiceBase;
import org.guildcode.application.service.authentication.dto.RefreshTokenRequestDto;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class RefreshTokenService extends ServiceBase implements ReactiveService<RefreshTokenRequestDto> {

    @Override
    public Uni<ResponseResult> handle(RefreshTokenRequestDto request) {
        return null;
    }
}
