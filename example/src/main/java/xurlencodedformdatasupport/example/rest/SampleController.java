package xurlencodedformdatasupport.example.rest;

import behzadian.java.springboot.x.url.encoded.form.data.support.FormPostParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
public class SampleController {

    @PostMapping(value = "/long", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String longValue(@NotNull @FormPostParam("id") Long id){
        return id.toString();
    }
}
