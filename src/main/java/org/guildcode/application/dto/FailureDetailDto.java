package org.guildcode.application.dto;

public final class FailureDetailDto implements Dto {
    private String description;
    private String tag;
    private String code;

    public FailureDetailDto() {
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
