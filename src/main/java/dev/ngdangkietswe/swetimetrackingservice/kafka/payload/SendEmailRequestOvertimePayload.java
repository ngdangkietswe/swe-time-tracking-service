package dev.ngdangkietswe.swetimetrackingservice.kafka.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author ngdangkietswe
 * @since 2/26/2025
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendEmailRequestOvertimePayload implements Serializable {

    private String date;
    private String requester;
    private String approver;
    @JsonProperty("approver_email")
    private String approverEmail;
    @JsonProperty("total_hours")
    private Double totalHours;
    private String reason;
}
