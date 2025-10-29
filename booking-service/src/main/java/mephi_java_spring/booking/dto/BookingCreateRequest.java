package mephi_java_spring.booking.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class BookingCreateRequest {

    @NotNull
    private String requestId; // уникальный ID для идемпотентности

    private boolean autoSelect; // если true — выбираем комнату автоматически

    private Long roomId; // если autoSelect=false

    @NotNull
    @FutureOrPresent
    private LocalDate startDate;

    @NotNull
    @FutureOrPresent
    private LocalDate endDate;
}

