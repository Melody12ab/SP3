package com.lenovo.dao;

import com.lenovo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by xiaobai on 16-8-4.
 */
@Repository
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int getMatchCount(String userName,String password){
        String sql="select count(*) from auth_user where USERNAME=? and PASSWORD=?";
        return jdbcTemplate.queryForObject(sql,new Object[]{userName,password},Integer.class);
    }

    public User findUserByUserName(final String userName){
        String sql="select id,USERNAME,PASSWORD from auth_user where USERNAME=?";
        return jdbcTemplate.queryForObject(sql, new Object[]{userName}, new RowMapper<User>() {
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user=new User();
                user.setID(resultSet.getLong("id"));
                user.setUserName(resultSet.getString("USERNAME"));
                user.setPassword(resultSet.getString("PASSWORD"));
                return user;
            }
        });
    }

}
