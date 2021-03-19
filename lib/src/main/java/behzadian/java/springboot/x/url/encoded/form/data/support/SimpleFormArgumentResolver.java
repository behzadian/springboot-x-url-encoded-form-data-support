package behzadian.java.springboot.x.url.encoded.form.data.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

import static behzadian.java.springboot.x.url.encoded.form.data.support.ConvertUtility.Convert;

/**
 * Parses and extracts uploaded value from post body
 */
public class SimpleFormArgumentResolver implements HandlerMethodArgumentResolver {
	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		return methodParameter.getParameterAnnotation(FormPostParam.class) != null;
	}

	@Override
	public Object resolveArgument(
			@NotNull MethodParameter methodParameter,
			ModelAndViewContainer modelAndViewContainer,
			@NotNull NativeWebRequest nativeWebRequest,
			WebDataBinderFactory webDataBinderFactory
	) throws Exception {
		FormPostParam formPostParam = methodParameter.getParameterAnnotation(FormPostParam.class);
		if (formPostParam == null)
			throw new UnappliedFormPostParamException();
		String parameterName = formPostParam.name();
		if (parameterName.isEmpty())
			throw new UnspecifiedParameterNameException();
		CachedBodyHttpServletRequest nativeRequest = nativeWebRequest.getNativeRequest(CachedBodyHttpServletRequest.class);
		if (nativeRequest == null)
			throw new EmptyBodyException();
		String body = nativeRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		String keyValueStart = urlEncode(parameterName) + "=";
		String keyValueRaw = Arrays
				.stream(body.split("&"))
				.filter(x -> x.startsWith(keyValueStart))
				.findFirst()
				.orElseThrow(() -> new FormKeyNotFoundException(parameterName));
		KeyValue keyValue = parseKeyValue(keyValueRaw);
		return Convert(keyValue.getValue(), methodParameter.getParameterType());
	}

	private String urlEncode(String value) {
		try {
			return java.net.URLEncoder.encode(value, StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			throw new UrlEncodeException(value, e);
		}
	}

	@Data
	@AllArgsConstructor
	static class KeyValue {
		private String key;
		private String value;
	}

	private KeyValue parseKeyValue(@NotNull String both) {
		try {
			return parseKeyValueInternal(both);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	private KeyValue parseKeyValueInternal(@NotNull String both) throws UnsupportedEncodingException {
		String[] parts = both.split("=");
		if (parts.length == 0)
			throw new RuntimeException("");
		if (parts.length > 2)
			throw new RuntimeException("");
		String key = java.net.URLDecoder.decode(parts[0], StandardCharsets.UTF_8.name());
		String value = parts.length == 2 ? java.net.URLDecoder.decode(parts[1], StandardCharsets.UTF_8.name()) : null;

		return new KeyValue(key, value);
	}
}
