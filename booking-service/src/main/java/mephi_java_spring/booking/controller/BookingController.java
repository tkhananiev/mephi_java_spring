package mephi_java_spring.booking.controller;

import mephi_java_spring.booking.dto.BookingCreateRequest;
import mephi_java_spring.booking.dto.BookingResponse;
import mephi_java_spring.booking.entity.BookingEntity;
import mephi_java_spring.booking.security.JwtUserData;
import mephi_java_spring.booking.service.BookingOrchestrator;
import mephi_java_spring.booking.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookingController {

    private final BookingOrchestrator bookingOrchestrator;
    private final BookingService bookingService;

    @PostMapping("/booking")
    public BookingResponse createBooking(
            Authentication auth,
            @RequestBody @Valid BookingCreateRequest req
    ) {
        JwtUserData principal = (JwtUserData) auth.getPrincipal();

        // пока autoSelect упрощён — берём roomId из запроса
        Long chosenRoomId = req.isAutoSelect()
                ? req.getRoomId()
                : req.getRoomId();

        BookingEntity result = bookingOrchestrator.createAndConfirm(
                principal.userId(),
                req,
                chosenRoomId
        );

        return BookingResponse.builder()
                .id(result.getId())
                .roomId(result.getRoomId())
                .status(result.getStatus())
                .createdAt(result.getCreatedAt())
                .build();
    }

    @GetMapping("/booking/{id}")
    public BookingResponse getBooking(
            Authentication auth,
            @PathVariable Long id
    ) {
        JwtUserData principal = (JwtUserData) auth.getPrincipal();
        boolean isAdmin = principal.role().equals("ADMIN");

        BookingEntity b = bookingService.getByIdForUser(id, principal.userId(), isAdmin)
                .orElseThrow(() -> new RuntimeException("Not found or not allowed"));

        return BookingResponse.builder()
                .id(b.getId())
                .roomId(b.getRoomId())
                .status(b.getStatus())
                .createdAt(b.getCreatedAt())
                .build();
    }

    @GetMapping("/bookings")
    public java.util.List<BookingResponse> myBookings(Authentication auth) {
        JwtUserData principal = (JwtUserData) auth.getPrincipal();

        return bookingService.listUserBookings(principal.userId())
                .stream()
                .map(b -> BookingResponse.builder()
                        .id(b.getId())
                        .roomId(b.getRoomId())
                        .status(b.getStatus())
                        .createdAt(b.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
