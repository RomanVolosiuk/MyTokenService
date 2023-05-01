package ua.volosiuk.mytokenservice.exception;

public class BadMD5AlgorithmException extends RuntimeException {

    public BadMD5AlgorithmException(String message) {
        super(message);
    }
}
