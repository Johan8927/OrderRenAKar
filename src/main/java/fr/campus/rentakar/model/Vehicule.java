package fr.campus.rentakar.model;




public class Vehicule {

    private int id;
    private char type;
    private String model;
    private char brand;
    private char color;
    private int kilometers;
    private int horsePower;
    private float cargo;
    private int capacity;
    private int displacement;
    private char registration;
    private boolean isAvailable;

    // constructor


    public Vehicule(int id, char type, String model, char brand, char color, int kilometers, int horsePower, float cargo, int capacity, int displacement, char registration, boolean isAvailable) {
        this.id = id;
        this.type = type;
        this.model = model;
        this.brand = brand;
        this.color = color;
        this.kilometers = kilometers;
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

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
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
}
