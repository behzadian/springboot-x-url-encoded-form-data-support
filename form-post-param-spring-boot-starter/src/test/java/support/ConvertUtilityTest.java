package support;

import behzadian.java.springboot.x.url.encoded.form.data.support.ConvertUtility;
import org.junit.jupiter.api.Test;

class ConvertUtilityTest {
    @Test
    public void longValues() {
        Object converted = ConvertUtility.Convert("12", long.class);
        assert (long)converted == 12L;
    }
}