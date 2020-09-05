package org.guildcode.application.result;

public enum ResponseStatus {
    OK,
    CREATED,
    ACCEPTED,
    NO_CONTENT,
    BAD_REQUEST,
    UNPROCESSED,
    UNAUTHORIZED,
    NOT_FOUND,
    INTERNAL_SERVER_ERROR;

    private ResponseStatus() {
    }

    public boolean success() {
        return this == OK || this == CREATED || this == ACCEPTED || this == NO_CONTENT;
    }

    public boolean failure() {
        return !this.success();
    }

    public Integer toNumber() {
        switch (this) {
            case OK:
                return 200;
            case CREATED:
                return 201;
            case ACCEPTED:
                return 202;
            case NO_CONTENT:
                return 204;
            case BAD_REQUEST:
                return 400;
            case UNPROCESSED:
                return 422;
            case UNAUTHORIZED:
                return 401;
            case NOT_FOUND:
                return 404;
            case INTERNAL_SERVER_ERROR:
                return 500;
            default:
                throw new IllegalStateException();
        }
    }

    public String toNumberString() {
        return this.toNumber().toString();
    }
}
