package dev.chatree.smarthomeapi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Inventory")
public class InventoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Brand")
    private String brand;

    @Column(name = "Quantity")
    private Double quantity;

    @Column(name = "MaxQuantity")
    private Double maxQuantity;

    @Column(name = "Unit")
    private String unit;

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
