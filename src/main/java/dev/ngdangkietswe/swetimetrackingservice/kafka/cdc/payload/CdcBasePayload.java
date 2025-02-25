package dev.ngdangkietswe.swetimetrackingservice.kafka.cdc.payload;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author ngdangkietswe
 * @since 2/25/2025
 */

@Getter
@Setter
public abstract class CdcBasePayload<E extends Serializable> implements Serializable {

    private UUID id;

    public abstract E toEntity();
}
