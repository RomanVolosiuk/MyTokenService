package ua.volosiuk.mytokenservice.exception;

public class RequiresAuthorisationException extends RuntimeException{
    public RequiresAuthorisationException(String message) {
        super(message);
    }

}
