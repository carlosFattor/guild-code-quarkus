package org.guildcode.application.result;

import java.util.List;

public interface SubjectToError {
    boolean hasSucceeded();

    List<FailureDetail> getFailureDetails();
}
