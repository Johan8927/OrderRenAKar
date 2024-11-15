package fr.campus.rentakar.model;


import java.time.LocalDate;

public class Vehicle {

    private int id;
    private String type;
    private String model;
    private String brand;
    private String color;
    private int kilometers;
    private int horsePower;
    private int cargo;

    public Vehicle() {
    }

    private int capacity;
    private int displacement;
    private String registration;
    private boolean isAvailable;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getKilometers() {
        return kilometers;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    public int getCargo() {
        return cargo;
    }

    public void setCargo(int cargo) {
        this.cargo = cargo;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getDisplacement() {
        return displacement;
    }

    public void setDisplacement(int displacement) {
        this.displacement = displacement;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
    public boolean isAvailable() {
        return isAvailable;
    }

    public boolean isAvailable(LocalDate startingOrderDate, LocalDate endingOrderDate) {
        return startingOrderDate.isAfter(LocalDate.now()) && endingOrderDate.isAfter(startingOrderDate) && isAvailable;
    }


}



