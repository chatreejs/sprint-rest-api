package dev.chatree.smarthomeapi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name = "permission")
public class PermissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "home_id")
    private long homeId;

    @Column(name = "account_id")
    private long accountId;

    @ElementCollection
    @CollectionTable(name = "permission", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "permission")
    private Set<String> permissions;
}
