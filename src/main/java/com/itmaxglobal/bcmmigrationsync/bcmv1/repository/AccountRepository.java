package com.itmaxglobal.bcmmigrationsync.bcmv1.repository;

import com.itmaxglobal.bcmmigrationsync.bcmv1.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
