package com.sjlh.hotel.common.push;

import java.time.LocalDateTime;

/**
 * Factory generated by hbm2java
 */
public class Factory  implements java.io.Serializable {


     private Long id;
     private Person person;
     private String name;
     private String location;
     private Double latitude;
     private Double longtitude;
     private LocalDateTime createTime;

    public Factory() {
    }

    public Factory(Person person, String name, String location, Double latitude, Double longtitude, LocalDateTime createTime) {
       this.person = person;
       this.name = name;
       this.location = location;
       this.latitude = latitude;
       this.longtitude = longtitude;
       this.createTime = createTime;
    }
   
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return this.person;
    }
    
    public void setPerson(Person person) {
        this.person = person;
    }

    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getLocation() {
        return this.location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }

    public Double getLatitude() {
        return this.latitude;
    }
    
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongtitude() {
        return this.longtitude;
    }
    
    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
    }

    public LocalDateTime getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}