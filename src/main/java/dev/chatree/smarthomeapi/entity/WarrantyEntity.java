package dev.chatree.smarthomeapi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Warranty")
public class WarrantyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "Brand")
    private String brand;

    @Column(name = "ProductName")
    private String productName;

    @Column(name = "ProductNumber")
    private String productNumber;

    @Column(name = "Model")
    private String model;

    @Column(name = "SerialNumber")
    private String serialNumber;

    @Column(name = "PurchaseDate")
    private LocalDate purchaseDate;

    @Column(name = "WarrantyDate")
    private LocalDate warrantyDate;

    @Column(name = "CreateDate")
    private LocalDateTime createDate;

    @Column(name = "UpdateDate")
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "HomeId")
    private HomeEntity home;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createDate = now;
        this.updateDate = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updateDate = LocalDateTime.now();
    }
}
