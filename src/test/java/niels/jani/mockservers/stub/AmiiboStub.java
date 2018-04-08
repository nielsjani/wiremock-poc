package niels.jani.mockservers.stub;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;

public class AmiiboStub extends AbstractStub {

    @Override
    protected int getPort() {
        return 2776;
    }

    @Override
    protected void configure() {
        getWireMockServer().stubFor(
                get(urlPathMatching("/api/amiibo.*"))
                        .willReturn(aResponse()
                                .withBody("Some info about $(myVar)")
                                .withStatus(200)
                                .withTransformers("body-transformer")
                                .withTransformerParameter("urlRegex", "/api/amiibo\\?name=(?<myVar>.*?)")
                        ));
        getWireMockServer().stubFor(
                get(urlPathEqualTo("/api/amiibo"))
                        .withQueryParam("name", equalTo("niels"))
                        .willReturn(ok("You shouldn't be asking about things you don't understand")));
    }
}
