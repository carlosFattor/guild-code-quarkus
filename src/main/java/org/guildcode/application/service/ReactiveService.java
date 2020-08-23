package org.guildcode.application.service;

import io.smallrye.mutiny.Uni;
import org.guildcode.application.dto.Dto;
import org.guildcode.application.result.ResponseResult;

public interface ReactiveService<RequestDto extends Dto> {
    Uni<ResponseResult> handle(RequestDto var1);
}
