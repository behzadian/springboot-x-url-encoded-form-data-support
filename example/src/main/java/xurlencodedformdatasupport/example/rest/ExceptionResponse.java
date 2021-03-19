package xurlencodedformdatasupport.example.rest;

import lombok.Getter;

import java.io.PrintWriter;
import java.io.StringWriter;

@Getter
public class ExceptionResponse<TValue> {

    private final String Message;
    private final String Details;
    private final String Type;
    private final TValue Value;

    public static String details(Exception exception, boolean detailed) {
        if (!detailed)
            return null;
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    public ExceptionResponse(String message, Exception exception, boolean detailed, TValue value) {
        this.Message = message;
        this.Details = details(exception, detailed);
        this.Type = exception.getClass().getSimpleName();
        this.Value = value;
    }

    public ExceptionResponse(Exception exception, boolean detailed, TValue value) {
        this.Message = exception.getLocalizedMessage();
        this.Details = details(exception, detailed);
        this.Type = exception.getClass().getSimpleName();
        this.Value = value;
    }
}
