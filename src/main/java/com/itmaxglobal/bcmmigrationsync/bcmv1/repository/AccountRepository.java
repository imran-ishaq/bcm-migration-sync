package com.itmaxglobal.bcmmigrationsync.bcmv1.repository;

import com.itmaxglobal.bcmmigrationsync.bcmv1.entity.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE billing.account SET is_migrated = 1 WHERE id = :id",nativeQuery = true)
    void updateIsMigrated(@Param("id")Long id);

}
