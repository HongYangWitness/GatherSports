package com.example.sportgather.controller;

import com.example.sportgather.domain.Court;
import com.example.sportgather.domain.Sport;
import com.example.sportgather.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/hi")
public class AController {
    private final ReservationService reservationService;
    @Autowired
    public AController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
    @GetMapping
    public String saybye(@ModelAttribute("Sport") Sport sport, Model model){

        List<String> list = reservationService.findSportNameThathasCourtbyAll();
        //System.out.println(list.size());
        model.addAttribute("SportName_List", list);

        return "reservation";
    }

    @PostMapping
    public String sayHello(@ModelAttribute("Court") Court court, Sport sport, Model model) {
//        ModelAndView modelAndView = new ModelAndView();
//
//        modelAndView.setViewName("reservation");
//
//        modelAndView.addObject("Location_List", reservationService.findLocationByAll());
//
//        //System.out.println("test");
        List<Court > list;
        String sportName = sport.getSportName();
//        if (location.length()==0){
//            list = reservationService.findCourtNameByAll();
//        }
//        else{
//            list = reservationService.findCourtNameByLocation(location);
//        }
        list = reservationService.findCourtNameBySportName(sportName);


        model.addAttribute("Court_Name", list);

        return "reservation";

    }
}
