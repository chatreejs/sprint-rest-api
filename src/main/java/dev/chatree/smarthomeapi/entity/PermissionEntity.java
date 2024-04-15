package dev.chatree.smarthomeapi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name = "Permission")
public class PermissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "HomeId")
    private long homeId;

    @Column(name = "AccountId")
    private long accountId;

    @ElementCollection
    @CollectionTable(name = "Permission", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "Permission")
    private Set<String> permissions;
}
