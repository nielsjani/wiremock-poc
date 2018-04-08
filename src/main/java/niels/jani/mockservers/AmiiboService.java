package niels.jani.mockservers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class AmiiboService {

    @Value(value = "${amiibo.api.url}")
    private String baseUrl;

    public String getAmiibo(String name) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> forEntity = restTemplate.getForEntity(baseUrl + "?name=" + name, String.class);
            return forEntity.getBody();
        } catch (HttpClientErrorException e) {
            return "Nothing found on " + name;
        }
    }

}
