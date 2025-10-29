package mephi_java_spring.hotel.repository;

import mephi_java_spring.hotel.entity.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<HotelEntity, Long> {}
