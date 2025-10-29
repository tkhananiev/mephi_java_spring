package mephi_java_spring.booking.service;

import mephi_java_spring.booking.entity.BookingEntity;
import mephi_java_spring.booking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    public Optional<BookingEntity> getByIdForUser(Long bookingId, Long userId, boolean isAdmin) {
        return bookingRepository.findById(bookingId)
                .filter(b -> isAdmin || b.getUserId().equals(userId));
    }

    public java.util.List<BookingEntity> listUserBookings(Long userId) {
        return bookingRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Transactional
    public BookingEntity createPendingIfAbsent(BookingEntity draft) {
        return bookingRepository.findByRequestId(draft.getRequestId())
                .orElseGet(() -> bookingRepository.save(draft));
    }

    @Transactional
    public BookingEntity markConfirmed(BookingEntity b) {
        b.setStatus("CONFIRMED");
        return bookingRepository.save(b);
    }

    @Transactional
    public BookingEntity markCancelled(BookingEntity b) {
        b.setStatus("CANCELLED");
        return bookingRepository.save(b);
    }

    @Transactional
    public void cancelBookingForUser(Long bookingId, Long requesterId, boolean isAdmin) {
        bookingRepository.findById(bookingId)
                .filter(b -> isAdmin || b.getUserId().equals(requesterId))
                .ifPresent(b -> {
                    b.setStatus("CANCELLED");
                    b.setCreatedAt(OffsetDateTime.now());
                    bookingRepository.save(b);
                });
    }
}
