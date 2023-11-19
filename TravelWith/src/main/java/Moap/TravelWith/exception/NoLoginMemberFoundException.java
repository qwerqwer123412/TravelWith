package Moap.TravelWith.exception;

public class NoLoginMemberFoundException extends RuntimeException{
    public NoLoginMemberFoundException() {
        super();
    }

    public NoLoginMemberFoundException(String message) {
        super(message);
    }

    public NoLoginMemberFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoLoginMemberFoundException(Throwable cause) {
        super(cause);
    }

    protected NoLoginMemberFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
