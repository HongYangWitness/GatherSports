package com.example.sportgather.repository;

import com.example.sportgather.domain.Court;
import com.example.sportgather.domain.Reservation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface CourtRepository {
    @Select("SELECT Location FROM Court ")
    List<Court> findLocationByAll();
    @Select("SELECT Name  FROM Court Where Location = #{location} ")
    List<String> findCourtNameByLocation(@Param("location") String location);
    @Select("SELECT Name  FROM Court ")
    List<String> findCourtNameByAll();
    @Select("SELECT *  FROM Court Join Sport on Court.SportId = Sport.SportId  Where Sport.SportName = #{SportName} ")
    List<Court> findCourtNameBySportName(@Param("SportName") String SportName);
}