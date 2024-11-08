package fr.campus.rentakar.model;


import java.time.LocalDate;

public class Vehicule {

    private int id;
    private String type;
    private String model;
    private char brand;
    private char color;
    private static int kilometers;
    private int horsePower;
    private float cargo;
    private int capacity;
    private int displacement;
    private char registration;
    private boolean isAvailable;


    // constructor


    public Vehicule(int id, String type, String model, char brand, char color, int kilometers, int horsePower, float cargo, int capacity, int displacement, char registration, boolean isAvailable) {
        this.id = id;
        this.type = String.valueOf(type);
        this.model = model;
        this.brand = brand;
        this.color = color;
        Vehicule.kilometers = kilometers;
        this.horsePower = horsePower;
        this.cargo = cargo;
        this.capacity = capacity;
        this.displacement = displacement;
        this.registration = registration;
        this.isAvailable = isAvailable;
    }

    public Vehicule() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(char type) {
        this.type = String.valueOf(type);
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public char getBrand() {
        return brand;
    }

    public void setBrand(char brand) {
        this.brand = brand;
    }

    public char getColor() {
        return color;
    }

    public void setColor(char color) {
        this.color = color;
    }

    public static int getKilometers() {
        return kilometers;
    }

    public void setKilometers(int kilometers) {
        Vehicule.kilometers = kilometers;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    public float getCargo() {
        return cargo;
    }

    public void setCargo(float cargo) {
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

    public char getRegistration() {
        return registration;
    }

    public void setRegistration(char registration) {
        this.registration = registration;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public boolean isAvailable(LocalDate startingOrderDate, LocalDate endingOrderDate) {
        return startingOrderDate.isAfter(LocalDate.now()) && endingOrderDate.isAfter(startingOrderDate) && isAvailable;
    }

    public float getVolume() {
        return cargo;
    }

    public float getCylindree() {
        return capacity;

    }
}
