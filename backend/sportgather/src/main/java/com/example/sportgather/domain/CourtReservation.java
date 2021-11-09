package com.example.sportgather.domain;

import java.util.List;

public class CourtReservation {
    private String CourtId;
    private String CourtName;

    private List<String> AvailableTime;
    public String getCourtId() {
        return CourtId;
    }
    public void setCourtId(String courtId) {
        CourtId = courtId;
    }

    public String getCourtName() {
        return CourtName;
    }

    public void setCourtName(String courtName) {
        CourtName = courtName;
    }

    public List<String> getAvailableTime() {
        return AvailableTime;
    }

    public void setAvailableTime(List<String> availableTime) {
        AvailableTime = availableTime;
    }
}
