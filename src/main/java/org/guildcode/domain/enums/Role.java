package org.guildcode.domain.enums;

public enum  Role {
    ADMIN(1, "ADM"),
    USER(2, "USER");

    private Integer code;
    private String description;

    Role(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
