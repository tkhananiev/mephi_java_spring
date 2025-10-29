package mephi_java_spring.hotel.service;

import mephi_java_spring.hotel.entity.RoomEntity;
import mephi_java_spring.hotel.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public List<RoomEntity> getAll() {
        return roomRepository.findByAvailableTrueOrderByIdAsc();
    }

    public List<RoomEntity> getRecommended() {
        return roomRepository.findByAvailableTrueOrderByTimesBookedAscIdAsc();
    }

    public Optional<RoomEntity> findById(Long id) {
        return roomRepository.findById(id);
    }

    @Transactional
    public boolean confirmAvailability(Long id) {
        return roomRepository.findById(id)
            .filter(RoomEntity::isAvailable)
            .map(room -> {
                room.setAvailable(false);
                room.setTimesBooked(room.getTimesBooked() + 1);
                return true;
            })
            .orElse(false);
    }

    @Transactional
    public void release(Long id) {
        roomRepository.findById(id)
            .ifPresent(room -> room.setAvailable(true));
    }
}
