package ua.volosiuk.mytokenservice.exception;

public class MalformedCredentialsException extends RuntimeException {
    public MalformedCredentialsException(String message) {
        super(message);
    }

}
