package tacos.restclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tacos.model.Ingredient;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class TacoCloudClient {

    private RestTemplate restTemplate;

    public TacoCloudClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /*
     * Specify parameter as varargs argument
     */
    public Ingredient getIngredientById1(String ingredientId) {
        return restTemplate.getForObject("http://localhost:8080/api/ingredients/{id}",
                Ingredient.class, ingredientId);
    }
    /*
     * Specify parameters with a map
     */
    public Ingredient getIngredientById2(String ingredientId) {
        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("id", ingredientId);
        return restTemplate.getForObject("http://localhost:8080/api/ingredients/{id}",
                Ingredient.class, urlVariables);
    }

    /*
     * Request with URI instead of String
     */
    public Ingredient getIngredientById3(String ingredientId) {
        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("id", ingredientId);
        URI url = UriComponentsBuilder
                .fromHttpUrl("http://localhost:8080/api/ingredients/{id}")
                .build(urlVariables);
        return restTemplate.getForObject(url, Ingredient.class);
    }

    /*
     * Use getForEntity() instead of getForObject()
     */
     public Ingredient getIngredientById4(String ingredientId) {
       ResponseEntity<Ingredient> responseEntity =
           restTemplate.getForEntity("http://localhost:8080/api/ingredients/{id}",
               Ingredient.class, ingredientId);
       log.info("Fetched time: " +
               responseEntity.getHeaders().getDate());
       return responseEntity.getBody();
     }
}
