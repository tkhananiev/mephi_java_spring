package mephi_java_spring.hotel.controller;

import mephi_java_spring.hotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class InternalController {

    private final RoomService roomService;

    @PostMapping("/{id}/confirm-availability")
    public boolean confirm(@PathVariable Long id) {
        return roomService.confirmAvailability(id);
    }

    @PostMapping("/{id}/release")
    public void release(@PathVariable Long id) {
        roomService.release(id);
    }
}
