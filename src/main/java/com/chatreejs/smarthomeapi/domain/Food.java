package com.chatreejs.smarthomeapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "food")
@Getter
@Setter
public class Food implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double quantity;
    private String unit;
    private Date buyDate;
    private Date expireDate;
    private Date createDate;
    private Date updateDate;

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
