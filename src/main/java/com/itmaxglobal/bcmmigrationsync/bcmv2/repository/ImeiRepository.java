package com.itmaxglobal.bcmmigrationsync.bcmv2.repository;


import com.itmaxglobal.bcmmigrationsync.bcmv2.entity.Imei;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImeiRepository extends JpaRepository<Imei, Long> {
    List<Imei> findByImei(String imei);
    Imei findFirstByImei(String imei);
}
