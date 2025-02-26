package dev.ngdangkietswe.swetimetrackingservice.kafka.producer;

import dev.ngdangkietswe.swejavacommonshared.constants.KafkaConstant;
import dev.ngdangkietswe.swejavacommonshared.kafka.producer.BaseKafkaProducer;
import dev.ngdangkietswe.swetimetrackingservice.kafka.payload.SendEmailReplyOvertimePayload;
import dev.ngdangkietswe.swetimetrackingservice.kafka.payload.SendEmailRequestOvertimePayload;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author ngdangkietswe
 * @since 2/26/2025
 */

@Component
@Log4j2
public class TimeTrackingKafkaProducer extends BaseKafkaProducer<Object> {

    public TimeTrackingKafkaProducer(@Qualifier(KafkaConstant.JSON_KAFKA_TEMPLATE) KafkaTemplate<String, Object> kafkaTemplate) {
        super(kafkaTemplate);
    }

    /**
     * Send email request overtime.
     *
     * @param payload SendEmailRequestOvertimePayload
     */
    public void sendEmailRequestOvertime(SendEmailRequestOvertimePayload payload) {
        log.info("Sending email request overtime: [{}]", payload);
        super.sendMessage(KafkaConstant.TOPIC_EMAIL_REQUEST_OVERTIME, payload);
    }

    /**
     * Send email reply overtime.
     *
     * @param payload SendEmailReplyOvertimePayload
     */
    public void sendEmailReplyOvertime(SendEmailReplyOvertimePayload payload) {
        log.info("Sending email reply overtime: [{}]", payload);
        super.sendMessage(KafkaConstant.TOPIC_EMAIL_REPLY_OVERTIME, payload);
    }
}
