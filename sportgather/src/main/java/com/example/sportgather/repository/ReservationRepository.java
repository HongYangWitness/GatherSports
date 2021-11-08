package com.example.sportgather.repository;

import com.example.sportgather.domain.Reservation;
import com.example.sportgather.domain.SportStar;
import com.example.sportgather.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReservationRepository {

    @Select("SELECT * FROM Reservation")
    List<Reservation> findByAll();

    @Select("SELECT * FROM Reservation WHERE UserId = #{UserId}")
    List<Reservation> findByPk(@Param("UserId") String id);

    @Insert("Insert into Reservation(ReservationId, CourtId, UserId, BeginTime, EndTime) Values(#{ReservationId}," +
            "#{CourtId}, #{UserId}, #{BeginTime}, #{EndTime})")
    List<Reservation>  insertNewReservation(Reservation reservation);
    @Select("With newTable AS\n" +
            "(Select UserId, SportId,\n" +
            "row_number() OVER (PARTITION By SportId Order by cnt desc) as rk\n" +
            "From\n" +
            "(Select r.UserId, c.SportId, Count(r.ReservationId) as cnt\n" +
            "From Reservation as r natural join Court as c\n" +
            "Group by r.UserId, c.SportId) as a)\n" +
            "Select s.SportName, u.FirstName, u.LastName\n" +
            "From newTable as n Natural Join User as u natural join Sport as s\n" +
            "Where n.rk<= #{N}\n" +
            "Order by s.SportName asc\n" +
            "Limit 15;")
    List<SportStar> getSportStar(String topN);


    @Select("SELECT BeginTime FROM Reservation WHERE CourtId = #{courtId} AND BeginTime LIKE #{date} ")
    List<String> findTodayReservation(@Param("date") String date, @Param("courtId") String courtId);
}
