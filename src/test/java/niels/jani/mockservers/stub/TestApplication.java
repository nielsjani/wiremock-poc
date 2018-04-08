package niels.jani.mockservers.stub;

import niels.jani.mockservers.MockserversApplication;
import niels.jani.mockservers.stub.Stubservers;
import org.springframework.boot.builder.SpringApplicationBuilder;

public class TestApplication {

    public static void main(String[] args) {
        new Stubservers().start();
        new SpringApplicationBuilder()
                .sources(MockserversApplication.class)
                .profiles("test")
                .run(args);
    }
}
