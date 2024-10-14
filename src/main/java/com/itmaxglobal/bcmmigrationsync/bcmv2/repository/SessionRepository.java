package com.itmaxglobal.bcmmigrationsync.bcmv2.repository;


import com.itmaxglobal.bcmmigrationsync.bcmv2.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    Optional<Session> findFirstByImeiAndImsiAndMsisdnOrderByCreatedAtDesc(String imei, Long imsi, String msisdn);
    Optional<Session> findFirstByImei(String imei);

}
