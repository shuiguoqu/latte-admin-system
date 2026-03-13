package com.labelease.usermanagement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.labelease.usermanagement.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 用户数据访问层
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /** 根据用户名查询（含密码，用于登录） */
    @Select("SELECT * FROM t_user WHERE username = #{username} AND deleted = 0")
    User selectByUsername(@Param("username") String username);

    /** 根据用户名查询并加行锁（用于登录防爆破） */
    @Select("SELECT * FROM t_user WHERE username = #{username} AND deleted = 0 FOR UPDATE")
    User selectByUsernameForUpdate(@Param("username") String username);

    /** 原子增加登录失败次数，并返回更新后的值 */
    @Update("UPDATE t_user SET login_fail_count = login_fail_count + 1, update_time = NOW() WHERE id = #{userId}")
    int incrementLoginFailCount(@Param("userId") Long userId);

    /** 设置锁定时间 */
    @Update("UPDATE t_user SET locked_until = #{lockedUntil}, update_time = NOW() WHERE id = #{userId}")
    int setLockedUntil(@Param("userId") Long userId, @Param("lockedUntil") java.time.LocalDateTime lockedUntil);

    /** 清除登录失败记录 */
    @Update("UPDATE t_user SET login_fail_count = 0, locked_until = NULL, update_time = NOW() WHERE id = #{userId}")
    int clearLoginFailure(@Param("userId") Long userId);
}
