package dev.ngdangkietswe.swetimetrackingservice.kafka.cdc.consumer.auth;

import dev.ngdangkietswe.swejavacommonshared.constants.KafkaConstant;
import dev.ngdangkietswe.swetimetrackingservice.data.entity.CdcAuthUserEntity;
import dev.ngdangkietswe.swetimetrackingservice.data.repository.jpa.CdcAuthUserRepository;
import dev.ngdangkietswe.swetimetrackingservice.kafka.cdc.consumer.CdcBaseConsumer;
import dev.ngdangkietswe.swetimetrackingservice.kafka.cdc.payload.auth.CdcAuthUserPayload;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author ngdangkietswe
 * @since 2/25/2025
 */

@Component
public class CdcAuthUserConsumer extends CdcBaseConsumer<CdcAuthUserPayload, CdcAuthUserEntity, CdcAuthUserRepository> {

    public CdcAuthUserConsumer(CdcAuthUserRepository repository) {
        super(CdcAuthUserPayload.class, repository);
    }

    @KafkaListener(
            topics = KafkaConstant.TOPIC_CDC_AUTH_USERS,
            containerFactory = KafkaConstant.STRING_LISTENER_CONTAINER_FACTORY)
    public void consume(ConsumerRecord<String, String> record) {
        accept(record);
    }
}
