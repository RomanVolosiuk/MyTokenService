package ua.volosiuk.mytokenservice.exception;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String errorMessage;
    private LocalDateTime timestamp;

}
