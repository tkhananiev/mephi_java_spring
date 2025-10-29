package mephi_java_spring.hotel.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "hotels")
@Data
public class HotelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
}
