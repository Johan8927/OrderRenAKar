package fr.campus.rentakar.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "orders") // Renommage de la table pour éviter les conflits avec les mots-clés SQL
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Vehicle ID cannot be null")
    private Long vehiculeId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate startingOrderDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate endingOrderDate;

    private boolean hasBeenPayed;

    @Min(value = 0, message = "Caution must be a positive value")
    private float caution;

    @Min(value = 0, message = "Kilometer estimate must be a positive value")
    private int kilometerEstimate;

    // Constructeur par défaut
    public Order() {}

    // Constructeur avec tous les paramètres
    public Order(Long id, Long userId, Long vehiculeId, LocalDate startingOrderDate, LocalDate endingOrderDate,
                 boolean hasBeenPayed, float caution, int kilometerEstimate) {
        this.id = id;
        this.userId = userId;
        this.vehiculeId = vehiculeId;
        this.startingOrderDate = startingOrderDate;
        this.endingOrderDate = endingOrderDate;
        this.hasBeenPayed = hasBeenPayed;
        this.caution = caution;
        this.kilometerEstimate = kilometerEstimate;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getVehiculeId() {
        return vehiculeId;
    }

    public void setVehiculeId(Long vehiculeId) {
        this.vehiculeId = vehiculeId;
    }

    public LocalDate getStartingOrderDate() {
        return startingOrderDate;
    }

    public void setStartingOrderDate(LocalDate startingOrderDate) {
        this.startingOrderDate = startingOrderDate;
    }

    public LocalDate getEndingOrderDate() {
        return endingOrderDate;
    }

    public void setEndingOrderDate(LocalDate endingOrderDate) {
        this.endingOrderDate = endingOrderDate;
    }

    public boolean getHasBeenPayed() { // Nom adapté selon les conventions JavaBeans
        return hasBeenPayed;
    }

    public void setHasBeenPayed(boolean hasBeenPayed) {
        this.hasBeenPayed = hasBeenPayed;
    }

    public float getCaution() {
        return caution;
    }

    public void setCaution(float caution) {
        this.caution = caution;
    }

    public int getKilometerEstimate() {
        return kilometerEstimate;
    }

    public void setKilometerEstimate(int kilometerEstimate) {
        this.kilometerEstimate = kilometerEstimate;
    }

    // Méthode pour vérifier si les dates sont valides
    public boolean isValidOrder() {
        if (startingOrderDate == null || endingOrderDate == null) {
            return false;
        }
        return startingOrderDate.isBefore(endingOrderDate) && !startingOrderDate.isBefore(LocalDate.now());
    }


}
