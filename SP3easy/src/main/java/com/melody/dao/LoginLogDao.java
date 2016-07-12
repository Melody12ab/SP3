package com.melody.dao;

import com.melody.domain.Loginlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by xiaobai on 16-7-12.
 */
@Repository
public class LoginLogDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertLoginlog(Loginlog loginlog){
        String sqlStr="insert into t_login_log(user_id,ip,login_datetime) values(?,?,?)";
        jdbcTemplate.update(sqlStr,new Object[]{loginlog.getUserId(),loginlog.getIp(),loginlog.getLoginDate()});
    }

}
