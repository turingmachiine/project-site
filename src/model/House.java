package model;

import java.util.Date;

public class House {
    int id;
    String name;
    User creator;
    Date creationDate;
    HouseClass houseClass;
    String description;
    Location location;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setHouseClass(HouseClass houseClass) {
        this.houseClass = houseClass;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public House(int id, String name, User creator, Date creationDate, HouseClass houseClass, String description, Location location) {
        this.id = id;
        this.name = name;
        this.creator = creator;
        this.creationDate = creationDate;
        this.houseClass = houseClass;
        this.description = description;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getCreator() {
        return creator;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public HouseClass getHouseClass() {
        return houseClass;
    }

    public String getDescription() {
        return description;
    }

    public Location getLocation() {
        return location;
    }
}
