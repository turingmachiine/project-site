package model;

import java.util.Date;

public class House {
    int id;
    String name;
    User creator;
    String creationDate;
    HouseClass houseClass;
    String description;
    Location location;
    String image;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreator(User creator) {
        this.creator = creator;
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


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getCreator() {
        return creator;
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

    @Override
    public String toString() {
        return "House{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creator=" + creator.toString() +
                ", creationDate=" + creationDate +
                ", description='" + description + '\'' +
                '}';
    }

    public House(int id, String name, User creator, String creationDate, HouseClass houseClass, String description, Location location, String image) {
        this.id = id;
        this.name = name;
        this.creator = creator;
        this.creationDate = creationDate;
        this.houseClass = houseClass;
        this.description = description;
        this.location = location;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
