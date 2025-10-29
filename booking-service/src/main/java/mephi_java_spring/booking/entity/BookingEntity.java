package mephi_java_spring.booking.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "bookings",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_bookings_request_id", columnNames = "requestId")
       })
@Data
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long roomId;

    private LocalDate startDate;
    private LocalDate endDate;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Column(nullable = false)
    private String requestId;
}
