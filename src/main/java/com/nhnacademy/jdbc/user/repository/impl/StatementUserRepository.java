package com.nhnacademy.jdbc.user.repository.impl;

import com.nhnacademy.jdbc.user.domain.User;
import com.nhnacademy.jdbc.user.repository.UserRepository;
import com.nhnacademy.jdbc.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

@Slf4j
public class StatementUserRepository implements UserRepository {

    @Override
    public Optional<User> findByUserIdAndUserPassword(String userId, String userPassword) {
        //todo#1 아이디, 비밀번호가 일치하는 User 조회
        Connection conn = DbUtils.getConnection();
        ResultSet rs;
        User user = null;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(String.format("select * from jdbc_users where user_id=\'%s\' and user_password=\'%s\'", userId, userPassword));
            if(rs.next()) {
                user = new User(rs.getString("user_id"),
                        rs.getString("user_name"),
                        rs.getString("user_password"));
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findById(String userId) {
        //#todo#2-아이디로 User 조회
        Connection conn = DbUtils.getConnection();
        ResultSet rs;
        User user = null;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(String.format("select * from jdbc_users where user_id=\"%s\"", userId));
            if(rs.next()) {
                user = new User(rs.getString("user_id"),
                        rs.getString("user_name"),
                        rs.getString("user_password"));
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return Optional.ofNullable(user);
    }

    @Override
    public int save(User user) {
        //todo#3- User 저장
        Connection conn = DbUtils.getConnection();
        int result = 0;
        try {
            Statement stmt = conn.createStatement();
            result = stmt.executeUpdate(String.format("insert into jdbc_users %s values ('%s', '%s', '%s')", user.getKeys(), user.getUserId(), user.getUserName(), user.getUserPassword()));
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    @Override
    public int updateUserPasswordByUserId(String userId, String userPassword) {
        //todo#4-User 비밀번호 변경
        Connection conn = DbUtils.getConnection();
        int result = 0;
        try {
            Statement stmt = conn.createStatement();
            result = stmt.executeUpdate(String.format("update jdbc_users set user_password = '%s' where user_id = '%s'", userPassword, userId));
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    @Override
    public int deleteByUserId(String userId) {
        //todo#5 - User 삭제
        Connection conn = DbUtils.getConnection();
        int result = 0;
        try {
            Statement stmt = conn.createStatement();
            result = stmt.executeUpdate(String.format("delete from jdbc_users where user_id = '%s'", userId));
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }
}
