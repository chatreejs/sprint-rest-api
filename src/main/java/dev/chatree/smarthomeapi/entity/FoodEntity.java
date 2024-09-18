package dev.chatree.smarthomeapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Food")
public class FoodEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Brand")
    private String brand;

    @Column(name = "Quantity")
    private Double quantity;

    @Column(name = "Unit")
    private String unit;

    @Column(name = "BuyDate")
    private LocalDate buyDate;

    @Column(name = "ExpiryDate")
    private LocalDate expiryDate;

    @ManyToOne
    @JoinColumn(name = "CreateBy")
    private AccountEntity createBy;

    @ManyToOne
    @JoinColumn(name = "UpdateBy")
    private AccountEntity updateBy;

    @CreationTimestamp
    @Column(name = "CreateDate")
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = "UpdateDate")
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "HomeId")
    private HomeEntity home;
}
