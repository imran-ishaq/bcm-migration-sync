package com.itmaxglobal.bcmmigrationsync.bcmv2.repository;

import com.itmaxglobal.bcmmigrationsync.bcmv2.entity.SessionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionHistoryRepository extends JpaRepository<SessionHistory, Integer> {
}
