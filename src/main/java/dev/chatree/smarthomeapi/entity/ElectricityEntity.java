package dev.chatree.smarthomeapi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

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
    private Date invoiceDate;

    @Column(name = "Usage")
    private Integer usage;

    @Column(name = "FtRate")
    private Double ftRate;

    @Column(name = "ElectricityPrice")
    private Double electricityPrice;

    @Column(name = "ServiceCharge")
    private Double serviceCharge;

    @Column(name = "Ft")
    private Double ft;

    @Column(name = "Vat")
    private Double vat;

    @Column(name = "Total")
    private Double total;

    @Column(name = "CreateDate")
    private Date createDate;

    @Column(name = "UpdateDate")
    private Date updateDate;

    @ManyToOne
    @JoinColumn(name = "HomeId")
    private HomeEntity home;

    @PrePersist
    public void prePersist() {
        Date now = new Date();
        this.createDate = now;
        this.updateDate = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updateDate = new Date();
    }
}
