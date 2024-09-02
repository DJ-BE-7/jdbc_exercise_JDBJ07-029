package com.nhnacademy.jdbc.user.repository.impl;

import com.nhnacademy.jdbc.user.domain.User;
import com.nhnacademy.jdbc.user.repository.UserRepository;
import com.nhnacademy.jdbc.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Optional;

@Slf4j
public class PreparedStatementUserRepository implements UserRepository {
    @Override
    public Optional<User> findByUserIdAndUserPassword(String userId, String userPassword) {
        //todo#11 -PreparedStatement- 아이디 , 비밀번호가 일치하는 회원조회
        Connection conn = DbUtils.getConnection();
        ResultSet rs;
        User user = null;
        try {
            String sql = "select * from jdbc_users where user_id = ? and user_password = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, userPassword);
            rs = preparedStatement.executeQuery();
            if(rs.next()) {
                user = new User(rs.getString("user_id"),
                        rs.getString("user_name"),
                        rs.getString("user_password"));
            }
            preparedStatement.close();
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
        //todo#12-PreparedStatement-회원조회
        Connection conn = DbUtils.getConnection();
        ResultSet rs;
        User user = null;
        try {
            String sql = "select * from jdbc_users where user_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, userId);
            rs = preparedStatement.executeQuery();
            if(rs.next()) {
                user = new User(rs.getString("user_id"),
                        rs.getString("user_name"),
                        rs.getString("user_password"));
            }
            preparedStatement.close();
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
        //todo#13-PreparedStatement-회원저장
        Connection conn = DbUtils.getConnection();
        int result = 0;
        try {
            String sql = "insert into jdbc_users (user_id, user_name, user_password) values (?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user.getUserId());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.setString(3, user.getUserPassword());
            result = preparedStatement.executeUpdate();
            preparedStatement.close();
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
        //todo#14-PreparedStatement-회원정보 수정
        Connection conn = DbUtils.getConnection();
        int result = 0;
        try {
            String sql = "update jdbc_users set user_password = ? where user_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, userPassword);
            preparedStatement.setString(2, userId);
            result = preparedStatement.executeUpdate();
            preparedStatement.close();
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
        //todo#15-PreparedStatement-회원삭제
        Connection conn = DbUtils.getConnection();
        int result = 0;
        try {
            String sql = "delete from jdbc_users where user_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, userId);
            result = preparedStatement.executeUpdate();
            preparedStatement.close();
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
