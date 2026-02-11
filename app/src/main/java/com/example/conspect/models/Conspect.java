package com.example.conspect.models;

public class Conspect {
    private Long id;
    private String title;
    private String content;

    private String subject;

    private String createdAt;

    public Conspect(Long id, String title, String content, String subject, String createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.subject = subject;
        this.createdAt = createdAt;
    }

    public Conspect() {
    }

    public String getFormattedDate() {
        if (createdAt == null || createdAt.isEmpty()) {
            return "Дата не указана";
        }
        try {
            String dataPart = createdAt.split("T")[0];
            String timePart = createdAt.split("T")[1].substring(0, 5);

            String[] dateParts = dataPart.split("-");
            String formattedDate = dateParts[2] + "." + dateParts[1] + "." + dateParts[0];
            return "Дата: " + formattedDate + " " + timePart;
        } catch (Exception e) {
            return "Дата: " + createdAt;
        }
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
