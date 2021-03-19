package behzadian.java.springboot.x.url.encoded.form.data.support;

public class EmptyBodyException extends RuntimeException {
    public EmptyBodyException() {
        super("Request does not contains body");
    }
}
