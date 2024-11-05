package com.example.RentAKar.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "`order`")
public class Order {

    // constructeur général de "order"
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int id;
    private int userId;
    private int vehiculeId;
    private LocalDate startingOrderDate;
    private LocalDate EndingOrderDate;
    private boolean hasBeenPayed;
    private int caution;

    // Getters et Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVehiculeId() {
        return vehiculeId;
    }

    public void setVehiculeId(int vehiculeId) {
        this.vehiculeId = vehiculeId;
    }

    public LocalDate getStartingOrderDate() {
        return startingOrderDate;
    }

    public void setStartingOrderDate(LocalDate startingOrderDate) {
        this.startingOrderDate = startingOrderDate;
    }

    public boolean isHasBeenPayed() {
        return hasBeenPayed;
    }

    public void setHasBeenPayed(boolean hasBeenPayed) {
        this.hasBeenPayed = hasBeenPayed;
    }

    public LocalDate getEndingOrderDate() {
        return EndingOrderDate;
    }

    public void setEndingOrderDate(LocalDate endingOrderDate) {
        EndingOrderDate = endingOrderDate;
    }

    public int getCaution() {
        return caution;
    }

    public void setCaution(int caution) {
        this.caution = caution;
    }
}
