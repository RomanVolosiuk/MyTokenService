package ua.volosiuk.mytokenservice.exception;

public class BadSha256HmacOrKeyException extends RuntimeException {
    public BadSha256HmacOrKeyException(String message) {
        super(message);
    }
}
