package com.labelease.usermanagement.common;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 统一响应封装（code / message / data / timestamp）
 */
@Data
public class Result<T> {

    private int code;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    private Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    /** 成功（带数据） */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data);
    }

    /** 成功（带消息和数据） */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(200, message, data);
    }

    /** 失败 */
    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code, message, null);
    }

    /** 未授权 */
    public static <T> Result<T> unauthorized(String message) {
        return new Result<>(401, message, null);
    }

    /** 参数校验失败 */
    public static <T> Result<T> badRequest(String message) {
        return new Result<>(400, message, null);
    }

    /** 权限不足 */
    public static <T> Result<T> forbidden(String message) {
        return new Result<>(403, message, null);
    }
}
