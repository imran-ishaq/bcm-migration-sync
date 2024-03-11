package com.itmaxglobal.bcmmigrationsync.bcmv2.repository;


import com.itmaxglobal.bcmmigrationsync.bcmv2.entity.Imei;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImeiRepository extends JpaRepository<Imei, Long> {
    Optional<Imei> findFirstByImeiOrderByCreatedAtDesc (String imei);
}
