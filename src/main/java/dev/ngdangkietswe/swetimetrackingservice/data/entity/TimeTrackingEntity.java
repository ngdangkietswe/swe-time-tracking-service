package dev.ngdangkietswe.swetimetrackingservice.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author ngdangkietswe
 * @since 2/25/2025
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "time_tracking")
public class TimeTrackingEntity extends BaseEntity {

    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id")
    @ManyToOne(
            targetEntity = CdcAuthUserEntity.class,
            fetch = FetchType.LAZY)
    private CdcAuthUserEntity user;

    @Column(nullable = false)
    private Date date;

    @Column
    private Timestamp checkInTime;

    @Column
    private Timestamp checkOutTime;

    @Column
    private int status = 1;

    @Column
    private boolean isOverTime;

    @Column
    private Double overTimeHours;
}
