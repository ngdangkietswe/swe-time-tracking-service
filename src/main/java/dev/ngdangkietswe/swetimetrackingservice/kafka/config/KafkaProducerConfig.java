package dev.ngdangkietswe.swetimetrackingservice.kafka.config;

import dev.ngdangkietswe.swejavacommonshared.constants.KafkaConstant;
import dev.ngdangkietswe.swejavacommonshared.kafka.config.BaseKafkaProducerConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

/**
 * @author ngdangkietswe
 * @since 2/26/2025
 */

@Configuration
public class KafkaProducerConfig extends BaseKafkaProducerConfig {

    @Bean(name = KafkaConstant.JSON_KAFKA_TEMPLATE)
    public KafkaTemplate<String, Object> jsonKafkaTemplate(KafkaProperties kafkaProperties) {
        return this.getJsonKafkaTemplate(kafkaProperties);
    }

    @Bean(name = KafkaConstant.JSON_KAFKA_PRODUCER_FACTORY)
    public ProducerFactory<String, Object> jsonProducerFactory(KafkaProperties kafkaProperties) {
        return this.getJsonProducerFactory(kafkaProperties);
    }

    @Bean(name = KafkaConstant.STRING_KAFKA_TEMPLATE)
    public KafkaTemplate<String, Object> stringKafkaTemplate(KafkaProperties kafkaProperties) {
        return this.getStringKafkaTemplate(kafkaProperties);
    }

    @Bean(name = KafkaConstant.STRING_KAFKA_PRODUCER_FACTORY)
    public ProducerFactory<String, Object> stringProducerFactory(KafkaProperties kafkaProperties) {
        return this.getStringProducerFactory(kafkaProperties);
    }
}
