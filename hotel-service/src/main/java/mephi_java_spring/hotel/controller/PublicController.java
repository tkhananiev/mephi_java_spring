package mephi_java_spring.hotel.controller;

import mephi_java_spring.hotel.entity.HotelEntity;
import mephi_java_spring.hotel.entity.RoomEntity;
import mephi_java_spring.hotel.repository.HotelRepository;
import mephi_java_spring.hotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PublicController {

    private final HotelRepository hotelRepository;
    private final RoomService roomService;

    @GetMapping("/hotels")
    public List<HotelEntity> getHotels() {
        return hotelRepository.findAll();
    }

    @GetMapping("/rooms")
    public List<RoomEntity> getRooms() {
        return roomService.getAll();
    }

    @GetMapping("/rooms/recommend")
    public List<RoomEntity> getRecommended() {
        return roomService.getRecommended();
    }
}
