package com.nhnacademy.jdbc.student.repository.impl;

import com.nhnacademy.jdbc.student.domain.Student;
import com.nhnacademy.jdbc.student.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import java.sql.*;
import java.util.Optional;

@Slf4j
public class StudentRepositoryImpl implements StudentRepository {

    @Override
    public int save(Connection connection, Student student){
        //todo#2 학생등록
        int result = 0;
        try {
            String sql = "insert into jdbc_students (id, name, gender, age) values (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, student.getId());
            preparedStatement.setString(2, student.getName());
            preparedStatement.setString(3, student.getGender().toString());
            preparedStatement.setInt(4, student.getAge());
            result = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Optional<Student> findById(Connection connection,String id){
        //todo#3 학생조회
        ResultSet resultSet = null;
        Student student = null;
        try {
            String sql = "select * from jdbc_students where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                student = new Student(resultSet.getString("id"),
                        resultSet.getString("name"),
                        Student.GENDER.valueOf(resultSet.getString("gender")),
                        resultSet.getInt("age"),
                        resultSet.getTimestamp("created_at").toLocalDateTime());
            }
            preparedStatement.close();
            resultSet.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(student);
    }

    @Override
    public int update(Connection connection,Student student){
        //todo#4 학생수정
        int result = 0;
        try {
            String sql = "update jdbc_students set name = ?, gender = ?, age = ? where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getGender().toString());
            preparedStatement.setInt(3, student.getAge());
            preparedStatement.setString(4, student.getId());
            result = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int deleteById(Connection connection,String id){
        //todo#5 학생삭제
        int result = 0;
        try {
            String sql = "delete from jdbc_students where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            result = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}