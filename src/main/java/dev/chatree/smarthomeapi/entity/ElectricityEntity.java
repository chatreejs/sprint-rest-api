package dev.chatree.smarthomeapi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Electricity")
public class ElectricityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "InvoiceNumber")
    private String invoiceNumber;

    @Column(name = "InvoiceDate")
    private LocalDate invoiceDate;

    @Column(name = "Usage")
    private Integer usage;

    @Column(name = "FtRate")
    private BigDecimal ftRate;

    @Column(name = "ElectricityPrice")
    private BigDecimal electricityPrice;

    @Column(name = "ServiceCharge")
    private BigDecimal serviceCharge;

    @Column(name = "Ft")
    private BigDecimal ft;

    @Column(name = "Vat")
    private BigDecimal vat;

    @Column(name = "Total")
    private BigDecimal total;

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
