package com.ua.hackathon2024java.web.temporary;

public class ReportResponseDtoTemp {
    private Long id;
    private String date;
    private String status;
    private String text;
    private String name;
    private String region;
    private String city;
    private String category;

    public ReportResponseDtoTemp(Long id, String date, String status, String text, String name, String region, String city, String category) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.text = text;
        this.name = name;
        this.region = region;
        this.city = city;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public String getCity() {
        return city;
    }

    public String getCategory() {
        return category;
    }
}
