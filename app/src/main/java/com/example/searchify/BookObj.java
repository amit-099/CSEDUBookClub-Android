package com.example.searchify;

public class BookObj {
    private String name;
    private String category;
    private String owner;
    private String writer;
    private String availability;

    public BookObj(String name, String category, String owner, String writer, String availability) {
        this.name = name;
        this.category = category;
        this.owner = owner;
        this.writer = writer;
        this.availability = availability;
    }

    public BookObj() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "BookObj{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", owner='" + owner + '\'' +
                ", writer='" + writer + '\'' +
                ", availability='" + availability + '\'' +
                '}';
    }
}
