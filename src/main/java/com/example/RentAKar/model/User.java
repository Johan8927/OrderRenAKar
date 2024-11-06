package com.example.RentAKar.model;

public class User {

    private int id;
    String userName;
    String firstName;
    String dateOfBirth;
    int licenceId;
    int licenceYear;
    int hasOrderId;
    int hasVehiculeId;
    String adress;
    int postalCode;
    String password;
    String email;
    int phoneNumber;
    String role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getLicenceId() {
        return licenceId;
    }

    public void setLicenceId(int licenceId) {
        this.licenceId = licenceId;
    }

    public int getLicenceYear() {
        return licenceYear;
    }

    public void setLicenceYear(int licenceYear) {
        this.licenceYear = licenceYear;
    }

    public int getHasOrderId() {
        return hasOrderId;
    }

    public void setHasOrderId(int hasOrderId) {
        this.hasOrderId = hasOrderId;
    }

    public int getHasVehiculeId() {
        return hasVehiculeId;
    }

    public void setHasVehiculeId(int hasVehiculeId) {
        this.hasVehiculeId = hasVehiculeId;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User(int id, String userName, String firstName, String dateOfBirth, int licenceId, int licenceYear, int hasOrderId, int hasVehiculeId, String adress, int postalCode, String password, String email, int phoneNumber, String role) {

        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
        this.licenceId = licenceId;
        this.licenceYear = licenceYear;
        this.hasOrderId = hasOrderId;
        this.hasVehiculeId = hasVehiculeId;
        this.adress = adress;
        this.postalCode = postalCode;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;

    }
}
