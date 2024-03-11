package com.itmaxglobal.bcmmigrationsync.bcmv2.repository;

import com.itmaxglobal.bcmmigrationsync.bcmv2.entity.ImsiMsisdn;
import org.hibernate.annotations.processing.Find;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImsiMsisdnRepository extends JpaRepository<ImsiMsisdn, Long> {

    Optional<ImsiMsisdn> findFirstByImsiAndMsisdnOrderByCreatedAtDesc(String imsi, String msisdn);

}
