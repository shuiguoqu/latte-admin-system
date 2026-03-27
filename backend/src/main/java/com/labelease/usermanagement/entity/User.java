package com.labelease.usermanagement.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户实体
 */
@Data
@TableName("t_user")
public class User {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 用户名（登录用） */
    private String username;

    /** 密码（哈希存储，接口不返回） */
    @JsonIgnore
    private String password;

    /** 真实姓名 */
    private String realName;

    /** 邮箱 */
    private String email;

    /** 手机号 */
    private String phone;

    /** 状态：0-禁用，1-启用 */
    private Integer status;

    /** 角色：ADMIN-管理员，USER-普通用户 */
    private String role;

    /** 登录失败次数 */
    private Integer loginFailCount;

    /** 账号锁定截止时间 */
    private LocalDateTime lockedUntil;

    /** 逻辑删除标记 */
    @TableLogic
    private Integer deleted;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
