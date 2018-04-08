package niels.jani.mockservers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class AmiiboController {

    private final AmiiboService amiiboService;

    @Autowired
    public AmiiboController(AmiiboService amiiboService) {
        this.amiiboService = amiiboService;
    }

    @GetMapping(path = "/amiibo/{number}", produces = APPLICATION_JSON_VALUE)
    public AmiiboData getAmiibo(@PathVariable(value = "number") String number){
        return new AmiiboData(amiiboService.getAmiibo(number));
    }
}
