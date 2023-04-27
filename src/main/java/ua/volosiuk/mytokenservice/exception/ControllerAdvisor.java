package ua.volosiuk.mytokenservice.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Log4j2
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    private ResponseEntity<ErrorResponse> mainTemplateHandleException(Throwable ex, HttpStatus status) {
       ErrorResponse er = new ErrorResponse(ex.getMessage(), LocalDateTime.now());

       return new ResponseEntity<>(er, status);
    }

    @ExceptionHandler(MalformedCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleRequiresAuthorisationException(MalformedCredentialsException ex) {

        return mainTemplateHandleException(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RequiresAuthorisationException.class)
    public ResponseEntity<ErrorResponse> handleMalformedCredentialsException(RequiresAuthorisationException ex) {

        return mainTemplateHandleException(ex, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity<ErrorResponse> handleUserNotExistException(UserNotExistException ex) {

        return mainTemplateHandleException(ex, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<ErrorResponse> handleWrongPasswordException(WrongPasswordException ex) {

        return mainTemplateHandleException(ex, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserDisabledException.class)
    public ResponseEntity<ErrorResponse> handleUserDisabledException(UserDisabledException ex) {

        return mainTemplateHandleException(ex, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadMD5AlgorithmException.class)
    public ResponseEntity<ErrorResponse> handleBadMD5AlgorithmException(BadMD5AlgorithmException ex) {

        return mainTemplateHandleException(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadSha256HMACException.class)
    public ResponseEntity<ErrorResponse> handleBadSha256HMACException(BadSha256HMACException ex) {

        return mainTemplateHandleException(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
