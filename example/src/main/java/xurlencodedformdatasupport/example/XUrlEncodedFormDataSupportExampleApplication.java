package xurlencodedformdatasupport.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "xurlencodedformdatasupport.example.**",
        "xurlencodedformdatasupport.example.rest"
})
public class XUrlEncodedFormDataSupportExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(XUrlEncodedFormDataSupportExampleApplication.class, args);
    }
}
