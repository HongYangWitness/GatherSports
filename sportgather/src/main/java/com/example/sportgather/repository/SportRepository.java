package com.example.sportgather.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface SportRepository {
    @Select("SELECT DISTINCT SportName FROM Sport Natural Join Court ")
    List<String> findSportNameThathasCourtbyAll();
}
