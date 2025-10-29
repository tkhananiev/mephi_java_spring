package mephi_java_spring.booking.service;

import mephi_java_spring.booking.dto.BookingCreateRequest;
import mephi_java_spring.booking.entity.BookingEntity;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookingOrchestrator {

    private final BookingService bookingService;
    private final HotelClient hotelClient;

    @Transactional
    public BookingEntity createAndConfirm(Long userId, BookingCreateRequest req, Long chosenRoomId) {

        MDC.put("requestId", req.getRequestId());

        BookingEntity draft = new BookingEntity();
        draft.setUserId(userId);
        draft.setRoomId(chosenRoomId);
        draft.setStartDate(req.getStartDate());
        draft.setEndDate(req.getEndDate());
        draft.setStatus("PENDING");
        draft.setRequestId(req.getRequestId());

        BookingEntity pending = bookingService.createPendingIfAbsent(draft);

        boolean locked = hotelClient.confirmAvailability(pending.getRoomId());

        if (locked) {
            return bookingService.markConfirmed(pending);
        } else {
            hotelClient.release(pending.getRoomId());
            return bookingService.markCancelled(pending);
        }
    }
}
