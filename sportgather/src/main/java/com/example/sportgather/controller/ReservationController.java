package com.example.sportgather.controller;

import com.example.sportgather.domain.Reservation;
import com.example.sportgather.domain.SportStar;
import com.example.sportgather.repository.ReservationRepository;
import com.example.sportgather.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
    @GetMapping("/userid={id}")
    public  List<Reservation> getbyUserId(@PathVariable String id){
        return  reservationService.queryReservationByUserId(id);
    }
    @GetMapping("/update")
    public  List<Reservation> insertNewReservation(){
        Reservation reservation = new Reservation();
        reservation.setReservationId("2000");
        reservation.setCourtId("2");
        reservation.setUserId("0");
        String begintime =  new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        reservation.setBeginTime(new Date());
        reservation.setEndTime(new Date());
        reservationService.insertNewReservation(reservation);
        return reservationService.queryReservationByUserId("0");
    }
    @GetMapping("/getsportstar")
    public List<SportStar> getSportStar(){
        return  reservationService.getSportStar("2");
    }
}
