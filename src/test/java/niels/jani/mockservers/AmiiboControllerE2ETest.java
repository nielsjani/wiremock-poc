package niels.jani.mockservers;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.opentable.extension.BodyTransformer;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static com.jayway.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class AmiiboControllerE2ETest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(2776).extensions(new BodyTransformer()));

    @Before
    public void setupStubServer() {
        wireMockRule.stubFor(get(urlPathMatching("/api/amiibo.*"))
                .willReturn(aResponse()
                        .withBody("Some info about $(myVar)")
                        .withStatus(200)
                        .withTransformers("body-transformer")
                        .withTransformerParameter("urlRegex", "/api/amiibo\\?name=(?<myVar>.*?)")
                ));
    }

    @Test
    public void getAmiibo_givenAmiiboFound_thenReturnInfo() {
        AmiiboData actual = given()
                .when()
                .get("http://localhost:8080/amiibo/wario")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(AmiiboData.class);

        Assertions.assertThat(actual.data).contains("wario");
    }

    @Test
    public void getAmiibo_givenNoAmiiboFound_thenReturnNoInfoFound() {
        wireMockRule.stubFor(get(urlPathMatching("/api/amiibo.*"))
                .willReturn(aResponse()
                        .withBody("Overriding config from @Before... Nothing found on $(myVar)")
                        .withStatus(200)
                        .withTransformers("body-transformer")
                        .withTransformerParameter("urlRegex", "/api/amiibo\\?name=(?<myVar>.*?)")
                ));

        AmiiboData actual = given()
                .when()
                .get("http://localhost:8080/amiibo/kratos")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(AmiiboData.class);

        Assertions.assertThat(actual.data).contains("Nothing found on kratos");
    }
}
