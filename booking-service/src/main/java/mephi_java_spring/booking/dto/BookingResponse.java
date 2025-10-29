package mephi_java_spring.booking.dto;

import lombok.Builder;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
@Builder
public class BookingResponse {
    private Long id;
    private Long roomId;
    private String status;
    private OffsetDateTime createdAt;
}

