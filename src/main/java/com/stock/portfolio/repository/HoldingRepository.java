package com.stock.portfolio.repository;

import com.stock.portfolio.entity.HoldingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface HoldingRepository extends JpaRepository<HoldingEntity, Long> {

    List<HoldingEntity> findByUsername(String username);

    Optional<HoldingEntity> findByUsernameAndStock(String username, String stock);
}
