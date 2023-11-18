package dev.chatree.smarthomeapi.repository;

import dev.chatree.smarthomeapi.entity.WarrantyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarrantyRepository extends JpaRepository<WarrantyEntity, Long> {
}
