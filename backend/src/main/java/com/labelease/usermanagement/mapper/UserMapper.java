package com.labelease.usermanagement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.labelease.usermanagement.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;

/**
 * 用户数据访问层
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /** 根据用户名查询（含密码，用于登录） */
    @Select("SELECT * FROM t_user WHERE username = #{username} AND deleted = 0")
    User selectByUsername(@Param("username") String username);

    /**
     * 更新登录失败次数和锁定时间
     *
     * @param userId          用户ID
     * @param failedAttempts  失败次数
     * @param lockUntil       锁定截止时间
     */
    @Update("UPDATE t_user SET login_failed_attempts = #{failedAttempts}, lock_until = #{lockUntil}, update_time = NOW() WHERE id = #{userId}")
    void updateLoginAttempts(@Param("userId") Long userId,
                             @Param("failedAttempts") int failedAttempts,
                             @Param("lockUntil") LocalDateTime lockUntil);
}
