package mephi_java_spring.hotel.repository;

import mephi_java_spring.hotel.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    List<RoomEntity> findByAvailableTrueOrderByIdAsc();
    List<RoomEntity> findByAvailableTrueOrderByTimesBookedAscIdAsc();
}
