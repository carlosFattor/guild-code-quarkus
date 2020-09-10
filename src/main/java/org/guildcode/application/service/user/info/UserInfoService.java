package org.guildcode.application.service.user.info;

import io.smallrye.mutiny.Uni;
import org.guildcode.application.result.ResponseResult;
import org.guildcode.application.service.ReactiveService;
import org.guildcode.application.service.user.info.dto.UserInfoRequestDto;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class UserInfoService implements ReactiveService<UserInfoRequestDto> {
    @Override
    public Uni<ResponseResult> handle(UserInfoRequestDto request) {
        return null;
    }
}
