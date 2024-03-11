package com.itmaxglobal.bcmmigrationsync.bcmv2.repository;


import com.itmaxglobal.bcmmigrationsync.bcmv2.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    Optional<Session> findFirstByImeiAndImsiAndMsisdnAndCreatedAtGreaterThanOrderByCreatedAtDesc(String imei, String imsi, String msisdn, LocalDateTime createdAt);

}
