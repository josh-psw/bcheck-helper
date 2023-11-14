package ui.controller.submission.result;

import static ui.controller.submission.result.Status.FAILED;
import static ui.controller.submission.result.Status.SUCCESS;

public class Result<T> {
    private final Status status;
    private final T body;

    private Result(Status status, T body) {
        this.status = status;
        this.body = body;
    }

    public static <T> Result<T> failed(T body) {
        return new Result<>(FAILED, body);
    }

    public static <T> Result<T> success(T body) {
        return new Result<>(SUCCESS, body);
    }

    public Status status() {
        return status;
    }

    public T body() {
        return body;
    }
}
