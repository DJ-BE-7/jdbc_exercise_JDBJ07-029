package com.nhnacademy.jdbc.club.repository.impl;

import com.nhnacademy.jdbc.club.domain.Club;
import com.nhnacademy.jdbc.club.domain.ClubStudent;
import com.nhnacademy.jdbc.club.repository.ClubRegistrationRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Slf4j
public class ClubRegistrationRepositoryImpl implements ClubRegistrationRepository {

    @Override
    public int save(Connection connection, String studentId, String clubId) {
        //todo#11 - 핵생 -> 클럽 등록, executeUpdate() 결과를 반환
        int result = 0;
        try {
            String sql = "insert into jdbc_club_registrations (student_id, club_id) values (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, studentId);
            preparedStatement.setString(2, clubId);
            result = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int deleteByStudentIdAndClubId(Connection connection, String studentId, String clubId) {
        //todo#12 - 핵생 -> 클럽 탈퇴, executeUpdate() 결과를 반환
        int result = 0;
        try {
            String sql = "delete from jdbc_club_registrations where student_id = ? and club_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, studentId);
            preparedStatement.setString(2, clubId);
            result = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<ClubStudent> findClubStudentsByStudentId(Connection connection, String studentId) {
        //todo#13 - 핵생 -> 클럽 등록, executeUpdate() 결과를 반환
        ResultSet resultSet = null;
        List<ClubStudent> clubStudents = new ArrayList<>();
        ClubStudent clubStudent = null;
        try {
            String sql = "select s.id, s.name, c.club_id, c.club_name " +
                    "from jdbc_students s inner join jdbc_club_registrations r on s.id = r.student_id " +
                    "inner join jdbc_club c on r.club_id = c.club_id " +
                    "where s.id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, studentId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                clubStudent = new ClubStudent(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4));
                clubStudents.add(clubStudent);
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clubStudents;
    }

    @Override
    public List<ClubStudent> findClubStudents(Connection connection) {
        //todo#21 - join
        ResultSet resultSet = null;
        List<ClubStudent> clubStudents = new LinkedList<>();
        ClubStudent clubStudent = null;
        try {
            String sql = "select s.id, s.name, c.club_id, c.club_name " +
                    "from jdbc_students s inner join jdbc_club_registrations r on s.id = r.student_id " +
                    "inner join jdbc_club c on r.club_id = c.club_id";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                clubStudent = new ClubStudent(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4));
                clubStudents.add(clubStudent);
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
    }return clubStudents;}

    @Override
    public List<ClubStudent> findClubStudents_left_join(Connection connection) {
        //todo#22 - left join
        ResultSet resultSet = null;
        List<ClubStudent> clubStudents = new ArrayList<>();
        ClubStudent clubStudent = null;
        try {
            String sql = "select s.id, s.name, c.club_id, c.club_name " +
                    "from jdbc_students s left join jdbc_club_registrations r on s.id = r.student_id " +
                    "left join jdbc_club c on r.club_id = c.club_id";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                clubStudent = new ClubStudent(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4));
                clubStudents.add(clubStudent);
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clubStudents;
    }

    @Override
    public List<ClubStudent> findClubStudents_right_join(Connection connection) {
        //todo#23 - right join
        ResultSet resultSet = null;
        List<ClubStudent> clubStudents = new ArrayList<>();
        ClubStudent clubStudent = null;
        try {
            String sql = "select s.id, s.name, c.club_id, c.club_name " +
                    "from jdbc_students s inner join jdbc_club_registrations r on s.id = r.student_id " +
                    "right join jdbc_club c on r.club_id = c.club_id";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                clubStudent = new ClubStudent(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4));
                clubStudents.add(clubStudent);
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clubStudents;
    }

    @Override
    public List<ClubStudent> findClubStudents_full_join(Connection connection) {
        //todo#24 - full join = left join union right join
        ResultSet resultSet = null;
        List<ClubStudent> clubStudents = new ArrayList<>();
        ClubStudent clubStudent = null;
        try {
            String sql = "select s.id, s.name, c.club_id, c.club_name " +
                    "from jdbc_students s left join jdbc_club_registrations r on s.id = r.student_id " +
                    "left join jdbc_club c on r.club_id = c.club_id union " +
                    "select s.id, s.name, c.club_id, c.club_name " +
                    "from jdbc_students s inner join jdbc_club_registrations r on s.id = r.student_id " +
                    "right join jdbc_club c on r.club_id = c.club_id";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                clubStudent = new ClubStudent(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4));
                clubStudents.add(clubStudent);
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clubStudents;
    }

    @Override
    public List<ClubStudent> findClubStudents_left_excluding_join(Connection connection) {
        //todo#25 - left excluding join
        ResultSet resultSet = null;
        List<ClubStudent> clubStudents = new ArrayList<>();
        ClubStudent clubStudent = null;
        try {
            String sql = "select s.id, s.name, c.club_id, c.club_name " +
                    "from jdbc_students s left join jdbc_club_registrations r on s.id = r.student_id " +
                    "left join jdbc_club c on r.club_id = c.club_id " +
                    "where c.club_id is null";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                clubStudent = new ClubStudent(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4));
                clubStudents.add(clubStudent);
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clubStudents;
    }

    @Override
    public List<ClubStudent> findClubStudents_right_excluding_join(Connection connection) {
        //todo#26 - right excluding join
        ResultSet resultSet = null;
        List<ClubStudent> clubStudents = new ArrayList<>();
        ClubStudent clubStudent = null;
        try {
            String sql = "select s.id, s.name, c.club_id, c.club_name " +
                    "from jdbc_students s inner join jdbc_club_registrations r on s.id = r.student_id " +
                    "right join jdbc_club c on r.club_id = c.club_id " +
                    "where s.id is null;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                clubStudent = new ClubStudent(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4));
                clubStudents.add(clubStudent);
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clubStudents;
    }

    @Override
    public List<ClubStudent> findClubStudents_outher_excluding_join(Connection connection) {
        //todo#27 - outher_excluding_join = left excluding join union right excluding join
        ResultSet resultSet = null;
        List<ClubStudent> clubStudents = new LinkedList<>();
        ClubStudent clubStudent = null;
        try {
            String sql = "select s.id, s.name, c.club_id, c.club_name " +
                    "from jdbc_students s left join jdbc_club_registrations r on s.id = r.student_id " +
                    "left join jdbc_club c on r.club_id = c.club_id " +
                    "where c.club_id is null union " +
                    "select s.id, s.name, c.club_id, c.club_name " +
                    "from jdbc_students s inner join jdbc_club_registrations r on s.id = r.student_id " +
                    "right join jdbc_club c on r.club_id = c.club_id " +
                    "where s.id is null";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                clubStudent = new ClubStudent(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4));
                clubStudents.add(clubStudent);
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clubStudents;
    }
}