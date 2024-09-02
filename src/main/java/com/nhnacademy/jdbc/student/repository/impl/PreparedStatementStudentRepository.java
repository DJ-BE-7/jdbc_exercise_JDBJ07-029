package com.nhnacademy.jdbc.student.repository.impl;

import com.nhnacademy.jdbc.student.domain.Student;
import com.nhnacademy.jdbc.student.repository.StudentRepository;
import com.nhnacademy.jdbc.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Optional;

@Slf4j
public class PreparedStatementStudentRepository implements StudentRepository {

    @Override
    public int save(Student student){
        //todo#1 insert student
        Connection conn = DbUtils.getConnection();
        int result = 0;
        try {
            Statement stmt = conn.createStatement();
            result = stmt.executeUpdate(String.format("insert into jdbc_students %s values %s",student.getKeys(),student.getValues()));
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } return result;
    }

    @Override
    public Optional<Student> findById(String id){
        //todo#2 학생 조회
        Connection conn = DbUtils.getConnection();
        ResultSet resultSet = null;
        Student student = null;
        try {
            String sql = "select * from jdbc_students where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                student = new Student(resultSet.getString("id"),
                        resultSet.getString("name"),
                        Student.GENDER.valueOf(resultSet.getString("gender")),
                        resultSet.getInt("age"));
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } return Optional.ofNullable(student);
    }

    @Override
    public int update(Student student){
        //todo#3 학생 수정 , name 수정
        Connection conn = DbUtils.getConnection();
        int result = 0;
        try {
            String sql = "update jdbc_students set name = ?, gender = ?, age = ? where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getGender().toString());
            preparedStatement.setInt(3, student.getAge());
            preparedStatement.setString(4, student.getId());
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
        } return result;
    }


    @Override
    public int deleteById(String id){
        //todo#4 학생 삭제
        Connection conn = DbUtils.getConnection();
        int result = 0;
        Student student = null;
        try {
            String sql = "delete from jdbc_students where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, id);
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
        } return result;
    }
}
