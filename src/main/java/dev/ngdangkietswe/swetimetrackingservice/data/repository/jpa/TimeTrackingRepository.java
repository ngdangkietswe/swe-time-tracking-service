package dev.ngdangkietswe.swetimetrackingservice.data.repository.jpa;

import dev.ngdangkietswe.swetimetrackingservice.data.entity.TimeTrackingEntity;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * @author ngdangkietswe
 * @since 2/25/2025
 */

@Repository
public interface TimeTrackingRepository extends IBaseRepository<TimeTrackingEntity> {

    Optional<TimeTrackingEntity> findByUserIdAndDateAndCheckInTimeIsNotNull(UUID userId, Date date);
}
