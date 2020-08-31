package org.guildcode.application.dto;

import java.util.List;

public class FailureResponseDto implements Dto {
    private List<FailureDetailDto> details;

    public FailureResponseDto() {
    }

    public List<FailureDetailDto> getDetails() {
        return this.details;
    }

    public void setDetails(List<FailureDetailDto> details) {
        this.details = details;
    }
}

