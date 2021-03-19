package behzadian.java.springboot.x.url.encoded.form.data.support;

public class FormKeyNotFoundException extends RuntimeException {
    public FormKeyNotFoundException(String parameterName) {
        super("Key `" + parameterName + "` does not present in form body");
    }
}
