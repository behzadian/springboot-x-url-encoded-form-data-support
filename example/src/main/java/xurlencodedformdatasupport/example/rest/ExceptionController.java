package xurlencodedformdatasupport.example.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.UnexpectedTypeException;
import java.nio.file.AccessDeniedException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @Value("${exception.output.details:false}")
    private boolean detailed;

    private String headers(WebRequest request) {
        return StreamSupport
                .stream(Spliterators.spliteratorUnknownSize(request.getHeaderNames(), Spliterator.ORDERED), false)
                .map(x -> String.format("%s -> %s", x, String.join(",", Objects.requireNonNull(request.getHeaderValues(x)))))
                .collect(Collectors.joining("\r\n\t\t"));
    }

    private String params(WebRequest request) {
        return request
                .getParameterMap()
                .entrySet()
                .stream()
                .map(x -> String.format("%s -> %s", x.getKey(), x.getValue() == null ? "[NULL]" : String.join("|", x.getValue())))
                .collect(Collectors.joining("\r\n\t\t"));
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> defaultException(Exception exception, WebRequest request) {
        if (exception instanceof UnexpectedTypeException) {
            return handleUnexpectedTypeException((UnexpectedTypeException) exception, request);
        }
        logger.error("Caught Exception. \r\n" +
                        String.format("\trequest.getHeaders(): \r\n\t\t%s\r\n", headers(request)) +
                        String.format("\trequest.getRequestURI(): %s\r\n", (request instanceof ServletWebRequest) ? ((ServletWebRequest) request).getRequest().getRequestURI() : String.format("request is %s NOT HttpServletRequest", request.getClass().getName())) +
                        String.format("\trequest.getContextPath(): %s\r\n", request.getContextPath()) +
                        String.format("\trequest.getLocale(): %s\r\n", request.getLocale()) +
                        String.format("\trequest.getParameterMap(): \r\n\t\t%s\r\n", params(request)) +
                        String.format("\trequest.getParameterNames(): %s\r\n", StreamSupport.stream(Spliterators.spliteratorUnknownSize(request.getParameterNames(), Spliterator.ORDERED), false).collect(Collectors.joining(","))) +
                        String.format("\trequest.getRemoteUser(): %s\r\n", request.getRemoteUser()) +
                        "",
                exception
        );
        try {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            if (exception instanceof AccessDeniedException)
                status = HttpStatus.FORBIDDEN;

            return handleExceptionInternal(
                    exception,
                    new ExceptionResponse<Void>(
                            exception,
                            detailed,
                            null
                    ),
                    new HttpHeaders(),
                    status,
                    request
            );

        } catch (Exception e) {
            return new ResponseEntity<>(ExceptionResponse.details(e, detailed), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<Object> handleUnexpectedTypeException(UnexpectedTypeException exception, WebRequest request) {
        return handleExceptionInternal(
                exception,
                new ExceptionResponse<>(
                        exception.getLocalizedMessage(),
                        exception,
                        detailed,
                        null
                ),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }
}
