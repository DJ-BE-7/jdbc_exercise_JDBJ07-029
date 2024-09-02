package com.nhnacademy.jdbc.student.repository.impl;

import com.nhnacademy.jdbc.student.domain.Student;
import com.nhnacademy.jdbc.student.repository.StudentRepository;
import com.nhnacademy.jdbc.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

@Slf4j
public class StatementStudentRepository implements StudentRepository {

    @Override
    public int save(Student student){
        //todo#1 insert student
        Connection conn = DbUtils.getConnection();
        int result = 0;
        try {
            Statement stmt = conn.createStatement();
            result = stmt.executeUpdate(String.format("insert into jdbc_students %s values %s",student.getKeys(),student.getValues()));
            stmt.close();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Optional<Student> findById(String id){
        //todo#2 student 조회
        Connection conn = DbUtils.getConnection();
        ResultSet resultSet = null;
        Student student = null;
        try {
            Statement stmt = conn.createStatement();
            resultSet = stmt.executeQuery(String.format("select * from jdbc_students where id = \'%s\'", id));
            if (resultSet.next()) {
                student = new Student(resultSet.getString("id"),
                        resultSet.getString("name"),
                        Student.GENDER.valueOf(resultSet.getString("gender")),
                        resultSet.getInt("age"));
            }
            stmt.close();
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
        //todo#3 student 수정, name <- 수정합니다.
        Connection conn = DbUtils.getConnection();
        int result = 0;
        try {
            Statement stmt = conn.createStatement();
            result = stmt.executeUpdate(String.format("update jdbc_students set name='%s', gender='%s', age=%d where id = '%s'", student.getName(), student.getGender(), student.getAge(), student.getId()));
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
    public int deleteById(String id){
       //todo#4 student 삭제
        Connection conn = DbUtils.getConnection();
        int result = 0;
        Student student = null;
        try {
            Statement stmt = conn.createStatement();
            result = stmt.executeUpdate(String.format("delete from jdbc_students where id = \'%s\'", id));
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
}
