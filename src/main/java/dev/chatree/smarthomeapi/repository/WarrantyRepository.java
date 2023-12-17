package dev.chatree.smarthomeapi.repository;

import dev.chatree.smarthomeapi.entity.WarrantyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarrantyRepository extends JpaRepository<WarrantyEntity, Long> {
    List<WarrantyEntity> findAllByOrderByWarrantyDateAsc();
}
