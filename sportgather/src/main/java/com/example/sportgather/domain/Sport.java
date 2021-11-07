package com.example.sportgather.domain;

public class Sport {
    private String SportId;
    private  String SportName;
    private  String SportIntroduction;
    private String SportImage;

    public String getSportId() {
        return SportId;
    }

    public void setSportId(String sportId) {
        SportId = sportId;
    }

    public String getSportName() {
        return SportName;
    }

    public void setSportName(String sportName) {
        SportName = sportName;
    }

    public String getSportIntroduction() {
        return SportIntroduction;
    }

    public void setSportIntroduction(String sportIntroduction) {
        SportIntroduction = sportIntroduction;
    }

    public String getSportImage() {
        return SportImage;
    }

    public void setSportImage(String sportImage) {
        SportImage = sportImage;
    }
}