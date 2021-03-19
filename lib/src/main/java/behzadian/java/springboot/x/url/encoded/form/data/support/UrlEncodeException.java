package behzadian.java.springboot.x.url.encoded.form.data.support;

import java.io.UnsupportedEncodingException;

public class UrlEncodeException extends RuntimeException {
	public UrlEncodeException(String value, UnsupportedEncodingException e) {
	}
}
