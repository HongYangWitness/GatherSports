package com.example.sportgather.service;

import com.example.sportgather.domain.*;
import com.example.sportgather.repository.CourtRepository;
import com.example.sportgather.repository.ReservationRepository;
import com.example.sportgather.repository.SportRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CourtRepository courtRepository;
    private final  SportRepository sportRepository;
    public ReservationService(ReservationRepository reservationRepository, CourtRepository courtRepository, SportRepository sportRepository) {
        this.reservationRepository = reservationRepository;
        this.courtRepository = courtRepository;
        this.sportRepository = sportRepository;
    }

    public List<Reservation> queryReservationByUserId(String id) {
        List<Reservation> list = reservationRepository.findByPk(id);
        return list;
    }

    public List<Map.Entry<String, Integer>> querySportStar(){
        int top = 15;
        Map<String, Integer> map = new HashMap<>();
        List<Reservation> list = reservationRepository.findByAll();

        /* add info into a hashmap to count frequency */
        for (Reservation reservation : list){
            map.put(reservation.getUserId(), map.getOrDefault(reservation.getUserId(), 0) + 1);
        }

        /* priorityqueue for top 15 */
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(((o1, o2) -> o1.getValue() - o2.getValue()));
        for (Map.Entry<String, Integer> entry : map.entrySet()){
            if (pq.size() < top){
                pq.add(entry);
            } else if (pq.size() == top && entry.getValue() > pq.peek().getValue()){
                pq.poll();
                pq.add(entry);
            }
        }

        /* res to store the final result*/
        List<Map.Entry<String,Integer>> res = new ArrayList<>();
        while (!pq.isEmpty()){
            res.add(0, pq.poll());
        }
        return res;
    }
    public void insertNewReservation(String CourtId,  String UserId, String BeginTime)  {
        StringBuilder sb = new StringBuilder();
        sb.append(BeginTime.substring(0,11));
        sb.append(Integer.parseInt(BeginTime.substring(11, 13)) + 1);
        sb.append(BeginTime.substring(13, BeginTime.length()));
        String EndTime = sb.toString();
        Integer Reservation_Id = reservationRepository.findMaxReservationId();
        Reservation_Id = Reservation_Id+1;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date BeginTime_date = dateFormat.parse(BeginTime);
            Date EndTime_date = dateFormat.parse(EndTime);
            Reservation reservation = new Reservation();
            reservation.setBeginTime(BeginTime_date);
            reservation.setEndTime(EndTime_date);
            reservation.setUserId("24");
            reservation.setCourtId(CourtId.substring(CourtId.length()-1,CourtId.length()));
            reservation.setReservationId(Reservation_Id);
            reservationRepository.insertNewReservation(reservation);
        } catch (Exception e) {
            e.printStackTrace();
        }
  
 
        return ;
    }
    public List<SportStar> getSportStar(String topN) {
        List<SportStar> list = reservationRepository.getSportStar(topN);
        return list;
    }
    public List<Court> findLocationByAll() {
        List<Court> list = courtRepository.findLocationByAll();
        return list;
    }
    public List<String> findCourtNameByLocation(String location) {
        List<String> list = courtRepository.findCourtNameByLocation(location);
        return list;
    }
    public List<String> findCourtNameByAll( ) {
        List<String> list = courtRepository.findCourtNameByAll();
        return list;
    }

    public List<String> findSportNameThathasCourtbyAll( ) {
        List<String> list = sportRepository.findSportNameThathasCourtbyAll();
        return list;
    }
    public List<Court> findCourtNameBySportName(String SportName ) {
        List<Court> list = courtRepository.findCourtNameBySportName(SportName);
        return list;
    }
    public List<String> findAvailableTime(String courtName){
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String strDate = dateFormat.format(date).substring(0,10);

        // return all reserved time
        Set<String> reservedSet = new HashSet<>(reservationRepository.findTodayReservation(strDate + "%",courtName));

        // remove reserved time
        Set<String> availableSet = new HashSet<>();
        for (int i = 8; i <= 22; i++){
            String sb = strDate;
            if (i < 10){
                sb += " 0" + i + ":00:00";
            } else {
                sb += " " + i + ":00:00";
            }
            availableSet.add(sb);
        }

        // get all available time
        availableSet.removeAll(reservedSet);
        List<String> availableTime = new ArrayList<>(availableSet);
        availableTime.sort((String::compareTo));
        return availableTime;
    }
    public List<CourtReservation> findAvailableTimeBySport(String sportName){
        // the list that stores all CourtReservation
        List<CourtReservation> ans = new ArrayList<>();

        // all courts name of the selected sportname
        List<String> courtNames = courtRepository.findCourtsBySportName(sportName);
        List<String> courtId = courtRepository.findCourtsIdBySportName(sportName);
        for (int i =0; i<courtNames.size();i++){
            CourtReservation courtReservation = new CourtReservation();
            courtReservation.setCourtName(courtNames.get(i));
            //System.out.println(courtId.get(i));
            courtReservation.setCourtId(courtId.get(i));
            courtReservation.setAvailableTime(findAvailableTime(courtNames.get(i)));
            ans.add(courtReservation);
        }
        return ans;
    }
}
