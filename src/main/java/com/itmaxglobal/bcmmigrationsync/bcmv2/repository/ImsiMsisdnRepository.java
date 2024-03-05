package com.itmaxglobal.bcmmigrationsync.bcmv2.repository;

import com.itmaxglobal.bcmmigrationsync.bcmv2.entity.ImsiMsisdn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImsiMsisdnRepository extends JpaRepository<ImsiMsisdn, Long> {
    List<ImsiMsisdn> findByImsi(String imsi);
    ImsiMsisdn findFirstByImsiAndMsisdn(String imsi,String msisdn);
}
