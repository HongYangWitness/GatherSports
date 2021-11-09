package com.example.sportgather.controller;

import com.example.sportgather.domain.Sport;
import com.example.sportgather.domain.User;
import com.example.sportgather.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(path = "match")
public class MatchViewController {

    private final MatchService matchService;

    @Autowired
    public MatchViewController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping(path = "/hobby/{id}")
    public List<Sport> fetchHobbies(@PathVariable("id") String UserId){
        System.out.println("fetchHobbies is called");
        return matchService.queryHobbies(UserId);
    }

    @GetMapping(path = "/hobby/mates/{id}")
    public List<User> findMatesByHobby(@PathVariable("id") String UserId){
        System.out.println("findMatesByHobby is called");
        return matchService.queryMatesByHobby(UserId);
    }

    @GetMapping(path="/samegender/mates/{id}")
    public List<User> findMatesWithSameGender(@PathVariable("id") String UserId) {
        System.out.println("findMatesWithSameGender is called");
        return matchService.queryMatesWithSameGender(UserId);
    }

    // params:
    // age = similar / nolimit
    // major = same / diff
    // gender = same / diff
//    @RequestMapping(method= RequestMethod.GET, value="/intermates")
//    public Set<String> findIntersectMates(@RequestParam Map<String, String> customQuery) {
//        System.out.println("findMates is called");
//        String age = customQuery.get("age")!=null?customQuery.get("age"):"";
//        String gender = customQuery.get("gender")!=null?customQuery.get("gender"):"";
//        String major = customQuery.get("major")!=null?customQuery.get("major"):"";
//
//        return matchService.queryIntersectMates(customQuery.get("id"), age, gender, major);
//    }

    // params:
    // age = similar / nolimit
    // major = same / diff
    // gender = same / diff
    @RequestMapping(method= RequestMethod.GET, value="/mates")
    public Set<String> findScoreMates(@RequestParam Map<String, String> customQuery) {
        System.out.println(customQuery);
        String age = customQuery.get("age")!=null?customQuery.get("age"):"";
        String gender = customQuery.get("gender")!=null?customQuery.get("gender"):"";
        String major = customQuery.get("major")!=null?customQuery.get("major"):"";

        return matchService.queryIntersectMates(customQuery.get("id"), age, gender, major);
    }
}