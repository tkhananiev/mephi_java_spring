package mephi_java_spring.hotel.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "rooms")
@Data
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private HotelEntity hotel;

    private String number;
    private boolean available = true;
    private int timesBooked = 0;
}
