package dev.ngdangkietswe.swetimetrackingservice.data.repository.jpa;

import dev.ngdangkietswe.swetimetrackingservice.data.entity.TimeTrackingEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author ngdangkietswe
 * @since 2/25/2025
 */

@Repository
public interface TimeTrackingRepository extends IBaseRepository<TimeTrackingEntity> {

    Optional<TimeTrackingEntity> findByUserIdAndDateAndCheckInTimeIsNotNull(UUID userId, Date date);

    List<TimeTrackingEntity> findAllByUserIdAndDateIsGreaterThanEqualAndDateIsLessThanEqual(UUID userId, Date startDate, Date endDate);

    Optional<TimeTrackingEntity> findByIdAndUserId(UUID id, UUID userId);

    @Query("SELECT t FROM TimeTrackingEntity t JOIN FETCH t.user WHERE t.id = :id")
    Optional<TimeTrackingEntity> findByIdFetchUser(UUID id);
}
