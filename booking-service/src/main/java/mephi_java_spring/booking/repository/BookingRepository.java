package mephi_java_spring.booking.repository;

import mephi_java_spring.booking.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    List<BookingEntity> findByUserIdOrderByCreatedAtDesc(Long userId);
    Optional<BookingEntity> findByRequestId(String requestId);
}
