package com.melody.dao;

import com.melody.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by xiaobai on 16-7-12.
 */
@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int getMatchCount(String userName,String password){
        String sqlStr="select count(*) from t_user where user_name=? and password=?";
        return jdbcTemplate.queryForObject(sqlStr,new Object[]{userName,password},Integer.class);
    }

    public User findUserByUserName(final String userName){
        String sqlStr="select user_id,user_name from t_user where user_name=?";
        return jdbcTemplate.queryForObject(sqlStr, new Object[]{userName}, new RowMapper<User>() {
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user=new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setUserName(resultSet.getString("user_name"));
                return user;
            }
        });
    }

    public void updateUserloginInfo(User user){
        String sqlStr="update t_user set last_visit=?,last_ip=? where user_id=?";
        jdbcTemplate.update(sqlStr,new Object[]{user.getLastVisit(),user.getLastIp(),user.getUserId()});
    }

}
