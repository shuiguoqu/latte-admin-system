package com.labelease.usermanagement.config;

import com.labelease.usermanagement.common.OrderStatusException;
import com.labelease.usermanagement.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器 - 统一错误响应
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** 参数校验异常 */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleValidation(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .reduce((a, b) -> a + "; " + b)
                .orElse("参数校验失败");
        log.warn("参数校验失败: {}", message);
        return Result.badRequest(message);
    }

    /** 通用异常 */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        log.error("系统异常: {}", e.getMessage(), e);
        return Result.error(500, "系统内部错误，请稍后重试");
    }

    /** 订单状态转换异常 */
    @ExceptionHandler(OrderStatusException.class)
    public Result<?> handleOrderStatusException(OrderStatusException e) {
        log.warn("订单状态转换失败: {}", e.getMessage());
        return Result.error(400, e.getMessage());
    }
}
