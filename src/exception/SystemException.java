package exception;

/**
 * Created by simin on 2017/10/25.
 */
public class SystemException extends RuntimeException {
    private Throwable cause;

    public SystemException(String msg, Throwable ex) {
        super(msg, ex);
        this.cause = ex;
    }
}
