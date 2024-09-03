package com.nhnacademy.jdbc.club.repository.impl;

import com.nhnacademy.jdbc.club.domain.Club;
import com.nhnacademy.jdbc.club.repository.ClubRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ClubRepositoryImpl implements ClubRepository {

    @Override
    public Optional<Club> findByClubId(Connection connection, String clubId) {
        //todo#3 club 조회
        ResultSet resultSet = null;
        Club club = null;
        try {
            String sql = "select * from jdbc_club where club_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, clubId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                club = new Club(resultSet.getString("club_id"),
                        resultSet.getString("club_name"),
                        resultSet.getTimestamp("club_created_at").toLocalDateTime());
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(club);
    }

    @Override
    public int save(Connection connection, Club club) {
        //todo#4 club 생성, executeUpdate() 결과를 반환
        int result = 0;
        try {
            String sql = "insert into jdbc_club (club_id, club_name, club_created_at) values (?, ?, DEFAULT)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, club.getClubId());
            preparedStatement.setString(2, club.getClubName());
            result = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int update(Connection connection, Club club) {
        //todo#5 club 수정, clubName을 수정합니다. executeUpdate()결과를 반환
        int result = 0;
        try {
            String sql = "update jdbc_club set club_name = ? where club_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, club.getClubName());
            preparedStatement.setString(2, club.getClubId());
            result = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int deleteByClubId(Connection connection, String clubId) {
        //todo#6 club 삭제, executeUpdate()결과 반환
        int result = 0;
        try {
            String sql = "delete from jdbc_club where club_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, clubId);
            result = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int countByClubId(Connection connection, String clubId) {
        //todo#7 clubId에 해당하는 club의 count를 반환
        ResultSet resultSet = null;
        int count = 0;
        try {
            String sql = "select count(*) from jdbc_club where club_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, clubId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }
}
