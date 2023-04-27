package ua.volosiuk.mytokenservice.exception;

public class BadSha256HMACException extends RuntimeException {
    public BadSha256HMACException(String message) {
        super(message);
    }
}
