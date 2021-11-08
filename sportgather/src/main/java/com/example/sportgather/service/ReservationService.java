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
    public List<Reservation> insertNewReservation(Reservation reservation) {
        List<Reservation> list = reservationRepository.insertNewReservation(reservation);
        return list;
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
    public List<String> findAvailableTime(String courtId){
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String strDate = dateFormat.format(date).substring(0,10);

        // return all reserved time
        Set<String> reservedSet = new HashSet<>(reservationRepository.findTodayReservation(strDate + "%",courtId));

        // remove reserved time
        Set<String> availableSet = new HashSet<>();
        for (int i = 8; i <= 22; i++){
            String sb = strDate;
            if (i < 10){
                sb += " 0" + i + ":00:00";
            } else {
                sb += strDate + " " + i + ":00:00";
            }
            availableSet.add(sb);
        }

        // get all available time
        availableSet.removeAll(reservedSet);
        List<String> availableTime = new ArrayList<>(availableSet);
        availableTime.sort((String::compareTo));
        return availableTime;
    }
}
