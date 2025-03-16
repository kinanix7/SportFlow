//in Entrainer.java
package com.sportflow.model;

public class Entrainer {
    private Integer id;
    private Integer userId;
    private String firstName;
    private String lastName;
    private String specialty;
    private User user; // Add user field

    // Getters and setters

    public User getUser() { //Add getter
        return user;
    }
    public void setUser(User user) { //And setter
        this.user = user;
    }
    //rest of getter and setter
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getSpecialty() {
        return specialty;
    }
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}