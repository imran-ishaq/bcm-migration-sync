
package com.itmaxglobal.bcmmigrationsync.bcmv2.repository;

import com.itmaxglobal.bcmmigrationsync.bcmv2.entity.ImsiOperatorConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImsiOperatorConfigRepository extends JpaRepository<ImsiOperatorConfig, Long>
{

   @Cacheable("ImsiOperatorConfigByImsiPrefix")
   Optional<ImsiOperatorConfig> findByImsiPrefix(String imsiPrefix);

}
