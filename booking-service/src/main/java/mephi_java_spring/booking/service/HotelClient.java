package mephi_java_spring.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Component
@RequiredArgsConstructor
public class HotelClient {

    @Value("${hotel-service.base-url}")
    private String baseUrl;

    @Value("${hotel-service.request.max-retries}")
    private int maxRetries;

    @Value("${hotel-service.request.retry-backoff-ms}")
    private long backoffMs;

    private final RestClient restClient = RestClient.create();

    public boolean confirmAvailability(Long roomId) {
        return retry(() -> {
            var response = restClient.post()
                    .uri(baseUrl + "/api/rooms/{id}/confirm-availability", roomId)
                    .retrieve()
                    .toEntity(Boolean.class);

            return response.getStatusCode().is2xxSuccessful()
                    && Boolean.TRUE.equals(response.getBody());
        });
    }

    public void release(Long roomId) {
        retry(() -> {
            restClient.post()
                    .uri(baseUrl + "/api/rooms/{id}/release", roomId)
                    .retrieve()
                    .toBodilessEntity();
            return true;
        });
    }

    private boolean retry(SupplierWithException<Boolean> supplier) {
        int attempt = 0;
        while (true) {
            try {
                return supplier.get();
            } catch (RestClientResponseException ex) {
                if (ex.getStatusCode() == HttpStatus.CONFLICT) {
                    return false;
                }
            } catch (Exception ex) {
            }
            attempt++;
            if (attempt > maxRetries) {
                return false;
            }
            try { Thread.sleep(backoffMs * attempt); } catch (InterruptedException ignored) {}
        }
    }

    @FunctionalInterface
    interface SupplierWithException<T> {
        T get() throws Exception;
    }
}
