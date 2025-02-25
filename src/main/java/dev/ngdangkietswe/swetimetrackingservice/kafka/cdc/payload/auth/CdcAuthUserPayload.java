package dev.ngdangkietswe.swetimetrackingservice.kafka.cdc.payload.auth;

import dev.ngdangkietswe.swetimetrackingservice.data.entity.CdcAuthUserEntity;
import dev.ngdangkietswe.swetimetrackingservice.kafka.cdc.payload.CdcBasePayload;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ngdangkietswe
 * @since 2/25/2025
 */

@Getter
@Setter
public class CdcAuthUserPayload extends CdcBasePayload<CdcAuthUserEntity> {

    private String username;
    private String email;

    @Override
    public CdcAuthUserEntity toEntity() {
        return CdcAuthUserEntity.builder()
                .id(this.getId())
                .username(this.username)
                .email(this.email)
                .build();
    }
}

