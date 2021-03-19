package behzadian.java.springboot.x.url.encoded.form.data.support;

import com.google.common.primitives.Longs;

import java.util.Arrays;
import java.util.Collections;

/**
 * Contains methods to convert a text value to its equivalent value
 */
public class ConvertUtility {

    public static <TResult> Object Convert(String value, Class<TResult> parameterType) {
        if (parameterType == long.class) {
            return Long.parseLong(value);
        }

        if (parameterType == int.class) {
            return parameterType.cast(Integer.parseInt(value));
        }

        if (parameterType == double.class) {
            return parameterType.cast(Double.parseDouble(value));
        }

        if (parameterType == float.class) {
            return parameterType.cast(Float.parseFloat(value));
        }

        if (parameterType == byte.class) {
            return parameterType.cast(Byte.parseByte(value));
        }

        if (parameterType == short.class) {
            return parameterType.cast(Short.parseShort(value));
        }

        if (parameterType == char.class) {
            if (value.length() > 1)
                throw new RuntimeException("");
            return parameterType.cast(Integer.parseInt(value));
        }

        return GenericConvert(value, parameterType);
    }

    public static <TResult> TResult GenericConvert(String value, Class<TResult> parameterType) {
        if (parameterType == String.class) {
            return parameterType.cast(value);
        }

        if (parameterType == Long.class) {
            return parameterType.cast(Long.parseLong(value));
        }

        if (parameterType == Integer.class) {
            return parameterType.cast(Integer.parseInt(value));
        }

        if (parameterType == Double.class) {
            return parameterType.cast(Double.parseDouble(value));
        }

        if (parameterType == Float.class) {
            return parameterType.cast(Float.parseFloat(value));
        }

        if (parameterType == Byte.class) {
            return parameterType.cast(Byte.parseByte(value));
        }

        if (parameterType == Short.class) {
            return parameterType.cast(Short.parseShort(value));
        }

        throw new UnsupportedTypeException(parameterType);
    }

    private static long tryParseLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (Exception e) {
            throw new RuntimeException("Unable to parse value `" + value + "` to long");
        }
    }
}
