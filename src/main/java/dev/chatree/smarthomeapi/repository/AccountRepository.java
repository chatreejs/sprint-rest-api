package dev.chatree.smarthomeapi.repository;

import dev.chatree.smarthomeapi.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    AccountEntity findBySubject(String subject);
}
