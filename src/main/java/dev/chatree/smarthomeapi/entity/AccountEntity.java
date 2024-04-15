package dev.chatree.smarthomeapi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "Account")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "Subject")
    private String subject;

    @Column(name = "Username")
    private String username;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "Email")
    private String email;

    @ManyToMany
    @JoinTable(
        name = "Home_Account",
        joinColumns = @JoinColumn(name = "AccountId"),
        inverseJoinColumns = @JoinColumn(name = "HomeId")
    )
    private Set<HomeEntity> homes;
}
