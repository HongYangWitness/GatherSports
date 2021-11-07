package com.example.sportgather.service;

import com.example.sportgather.domain.Reservation;
import com.example.sportgather.domain.SportStar;
import com.example.sportgather.domain.User;
import com.example.sportgather.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
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
}
