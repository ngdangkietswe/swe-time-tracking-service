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
public class SendEmailReplyOvertimePayload implements Serializable {

    private String date;
    private String approver;
    @JsonProperty("requester_email")
    private String requesterEmail;
    @JsonProperty("is_approved")
    private Boolean isApproved;
    private String reason;
}
