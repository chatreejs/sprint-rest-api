package dev.chatree.smarthomeapi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "electricity")
public class ElectricityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @Column(name = "usage")
    private Integer usage;

    @Column(name = "ft_rate")
    private BigDecimal ftRate;

    @Column(name = "electricity_price")
    private BigDecimal electricityPrice;

    @Column(name = "service_charge")
    private BigDecimal serviceCharge;

    @Column(name = "ft")
    private BigDecimal ft;

    @Column(name = "vat")
    private BigDecimal vat;

    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "home_id")
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
