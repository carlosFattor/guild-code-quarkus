package org.guildcode.application.result;

import java.util.Objects;

public final class FailureDetail {
    private final String description;
    private final String tag;
    private final String code;

    public FailureDetail(String description, String tag, String code) {
        Objects.requireNonNull(description, "description");
        if (description.trim().isEmpty()) {
            throw new IllegalArgumentException("description");
        } else {
            this.description = description;
            this.tag = tag;
            this.code = code;
        }
    }

    public FailureDetail(String description, String tag) {
        this(description, tag, (String)null);
    }

    public FailureDetail(String description) {
        this(description, (String)null, (String)null);
    }

    public String getDescription() {
        return this.description;
    }

    public String getTag() {
        return this.tag != null && !this.tag.isEmpty() ? this.tag : "_general_";
    }

    public String getCode() {
        return this.code;
    }
}
