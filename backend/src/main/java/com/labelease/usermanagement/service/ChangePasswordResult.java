package com.labelease.usermanagement.service;

public enum ChangePasswordResult {
    SUCCESS("密码修改成功"),
    USER_NOT_FOUND("用户不存在"),
    OLD_PASSWORD_INCORRECT("旧密码错误"),
    NEW_PASSWORD_SAME_AS_OLD("新密码不能与旧密码相同");

    private final String message;

    ChangePasswordResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return this == SUCCESS;
    }
}
