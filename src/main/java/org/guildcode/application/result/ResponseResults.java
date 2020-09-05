package org.guildcode.application.result;

import org.guildcode.application.dto.Dto;
import org.guildcode.application.dto.FailureDetailDto;
import org.guildcode.application.dto.FailureResponseDto;

import javax.validation.ConstraintViolation;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class ResponseResults {
    private ResponseResults() {
    }

    public static ResponseResult ok() {
        return new ResponseResult(ResponseStatus.OK);
    }

    public static ResponseResult ok(Dto response) {
        return new ResponseResult(ResponseStatus.OK, response);
    }

    public static ResponseResult created() {
        return new ResponseResult(ResponseStatus.CREATED);
    }

    public static ResponseResult created(Dto response) {
        return new ResponseResult(ResponseStatus.CREATED, response);
    }

    public static ResponseResult accepted() {
        return new ResponseResult(ResponseStatus.ACCEPTED);
    }

    public static ResponseResult accepted(Dto response) {
        return new ResponseResult(ResponseStatus.ACCEPTED, response);
    }

    public static ResponseResult noContent() {
        return new ResponseResult(ResponseStatus.NO_CONTENT);
    }

    public static ResponseResult notFound() {
        return new ResponseResult(ResponseStatus.NOT_FOUND);
    }

    public static ResponseResult notFound(Dto response) {
        return new ResponseResult(ResponseStatus.NOT_FOUND, response);
    }

    public static ResponseResult badRequest() {
        return new ResponseResult(ResponseStatus.BAD_REQUEST);
    }

    public static ResponseResult badRequest(FailureResponseDto response) {
        return new ResponseResult(ResponseStatus.BAD_REQUEST, response);
    }

    public static ResponseResult unauthorized() {
        return new ResponseResult(ResponseStatus.UNAUTHORIZED);
    }

    public static ResponseResult unauthorized(FailureResponseDto response) {
        return new ResponseResult(ResponseStatus.UNAUTHORIZED, response);
    }

    public static ResponseResult unprocessed() {
        return new ResponseResult(ResponseStatus.UNPROCESSED);
    }

    public static ResponseResult unprocessed(FailureResponseDto response) {
        return new ResponseResult(ResponseStatus.UNPROCESSED, response);
    }

    public static ResponseResult unprocessedFromDetailsList(List<FailureDetailDto> details) {
        FailureResponseDto badRequestResponse = new FailureResponseDto();
        badRequestResponse.setDetails(details);
        return new ResponseResult(ResponseStatus.UNPROCESSED, badRequestResponse);
    }

    public static ResponseResult unprocessedFromDetailsArray(FailureDetailDto... details) {
        Objects.requireNonNull(details);
        FailureResponseDto badRequestResponse = new FailureResponseDto();
        badRequestResponse.setDetails(Arrays.asList(details));
        return new ResponseResult(ResponseStatus.UNPROCESSED, badRequestResponse);
    }

    public static <T> ResponseResult unprocessedFromFailedResult(Result<T> result, Object hostData) {
        Objects.requireNonNull(result);
        if (result.hasSucceeded()) {
            throw new IllegalArgumentException("'result' have not failed.");
        } else {
            List<FailureDetailDto> details = (List<FailureDetailDto>) result.getFailureDetails().stream().map((failureDetail) -> {
                FailureDetailDto failureReqDetail = new FailureDetailDto();
                failureReqDetail.setDescription(failureDetail.getDescription());
                failureReqDetail.setTag(failureDetail.getTag());
                failureReqDetail.setCode(failureDetail.getCode());
                return failureReqDetail;
            }).collect(Collectors.toList());
            FailureResponseDto failureResponse = new FailureResponseDto();
            failureResponse.setDetails(details);
            return new ResponseResult(ResponseStatus.UNPROCESSED, failureResponse);
        }
    }

    public static <T> ResponseResult unprocessedFromConstraintViolation(Set<ConstraintViolation<T>> constraintViolations) {
        Objects.requireNonNull(constraintViolations);
        if (constraintViolations.isEmpty()) {
            throw new IllegalArgumentException("'result' have not failed.");
        } else {
            List<FailureDetailDto> details = (List<FailureDetailDto>) constraintViolations.stream().map((failure) -> {
                FailureDetailDto failureReqDetail = new FailureDetailDto();
                failureReqDetail.setDescription(String.join(" ", failure.getPropertyPath().toString(), failure.getMessage()));
                return failureReqDetail;
            }).collect(Collectors.toList());
            FailureResponseDto failureResponse = new FailureResponseDto();
            failureResponse.setDetails(details);
            return new ResponseResult(ResponseStatus.UNPROCESSED, failureResponse);
        }
    }

    public static <T> ResponseResult unprocessedFromFailedResult(Result<T> result) {
        return unprocessedFromFailedResult(result, (Object) null);
    }

    public static ResponseResult unprocessedFromDescriptions(String... descriptions) {
        List<FailureDetailDto> details = (descriptions == null) ? null : (List) Arrays.stream(descriptions).map((description) -> {
            FailureDetailDto failureDetail = new FailureDetailDto();
            failureDetail.setDescription(description);
            return failureDetail;
        }).collect(Collectors.toList());
        return unprocessedFromDetailsList(details);
    }

    public static ResponseResult badRequestFromDetailsList(List<FailureDetailDto> details) {
        FailureResponseDto badRequestResponse = new FailureResponseDto();
        badRequestResponse.setDetails(details);
        return new ResponseResult(ResponseStatus.BAD_REQUEST, badRequestResponse);
    }

    public static ResponseResult badRequestFromDescriptions(String... descriptions) {
        List<FailureDetailDto> details = descriptions == null ? null : (List) Arrays.stream(descriptions).map((description) -> {
            FailureDetailDto failureDetail = new FailureDetailDto();
            failureDetail.setDescription(description);
            return failureDetail;
        }).collect(Collectors.toList());
        return badRequestFromDetailsList(details);
    }
}
