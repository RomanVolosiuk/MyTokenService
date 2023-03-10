package ua.volosiuk.mytokenservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler({MalformedCredentialsException.class})
    public ResponseEntity<ErrorResponse> handleRequiresAuthorisationException(){
        ErrorResponse er = new ErrorResponse("Some problem with authorization header",
                LocalDateTime.now());

        return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RequiresAuthorisationException.class})
    public ResponseEntity<ErrorResponse> handleMalformedCredentialsException(){
        ErrorResponse er = new ErrorResponse("Some problem with username or password",
                LocalDateTime.now());

        return new ResponseEntity<>(er, HttpStatus.UNAUTHORIZED);
    }

}
