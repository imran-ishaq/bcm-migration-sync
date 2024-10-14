package com.itmaxglobal.bcmmigrationsync.bcmv2.repository;


import com.itmaxglobal.bcmmigrationsync.bcmv2.entity.Imei;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImeiRepository {
    Optional<Imei> findFirstByImeiOrderByCreatedAtDesc (String imei);
}
