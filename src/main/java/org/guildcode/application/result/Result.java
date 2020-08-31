package org.guildcode.application.result;

import java.util.Optional;

public interface Result<T> extends SubjectToError {
    Optional<T> getValue();
}
