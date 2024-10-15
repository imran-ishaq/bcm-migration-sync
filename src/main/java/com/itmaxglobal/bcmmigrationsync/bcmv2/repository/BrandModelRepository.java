package com.itmaxglobal.bcmmigrationsync.bcmv2.repository;

import com.itmaxglobal.bcmmigrationsync.bcmv2.entity.BrandModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandModelRepository extends JpaRepository<BrandModel, Long> {

    Optional<BrandModel> findByTacNumber(String tacNumber);

}
