package tacos.restclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tacos.model.Ingredient;
import tacos.web.api.model.TacoModel;

import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TacoCloudClient {

    private final RestTemplate restTemplate;

    private final Traverson traverson;

    public TacoCloudClient(RestTemplate restTemplate, Traverson traverson) {
        this.restTemplate = restTemplate;
        this.traverson = traverson;
    }

    /*
     * Specify parameter as varargs argument
     */
    public Ingredient getIngredientById1(String ingredientId) {
        return restTemplate.getForObject("http://localhost:8080/api/ingredients/{id}", Ingredient.class, ingredientId);
    }

    /*
     * Specify parameters with a map
     */
    public Ingredient getIngredientById2(String ingredientId) {
        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("id", ingredientId);
        return restTemplate.getForObject("http://localhost:8080/api/ingredients/{id}", Ingredient.class, urlVariables);
    }

    /*
     * Request with URI instead of String
     */
    public Ingredient getIngredientById3(String ingredientId) {
        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("id", ingredientId);
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/api/ingredients/{id}").build(urlVariables);
        return restTemplate.getForObject(url, Ingredient.class);
    }

    /*
     * Use getForEntity() instead of getForObject()
     */
    public Ingredient getIngredientById4(String ingredientId) {
        ResponseEntity<Ingredient> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/ingredients/{id}", Ingredient.class, ingredientId);
        log.info("Fetched time: " + responseEntity.getHeaders().getDate());
        return responseEntity.getBody();
    }

    public List<Ingredient> getAllIngredients() {
        return restTemplate.exchange("http://localhost:8080/ingredients", HttpMethod.GET, null, new ParameterizedTypeReference<List<Ingredient>>() {
        }).getBody();
    }

    //
    // PUT examples
    //
    public void putIngredient(Ingredient ingredient) {
        restTemplate.put("http://localhost:8080/api/ingredients/{id}", ingredient, ingredient.getId());
    }


    // delete example
    public void deleteIngredient(String ingredientId) {
        restTemplate.delete("http://localhost:8080/api/ingredients/{id}", ingredientId);
    }

    //
    // POST examples
    //
    public Ingredient createIngredient1(Ingredient ingredient) {
        return restTemplate.postForObject("http://localhost:8080/api/ingredients", ingredient, Ingredient.class);
    }

    /*
     * Alternate implementations...
     * The next two methods are alternative implementations of
     * createIngredient() as shown in chapter 6. If you'd like to try
     * any of them out, comment out the previous method and uncomment
     * the variant you want to use.
     */

    public java.net.URI createIngredient2(Ingredient ingredient) {
        return restTemplate.postForLocation("http://localhost:8080/api/ingredients", ingredient);
    }


    public Ingredient createIngredient3(Ingredient ingredient) {
        ResponseEntity<Ingredient> responseEntity = restTemplate.postForEntity("http://localhost:8080/api/ingredients", ingredient, Ingredient.class);
        log.info("New resource created at {}", responseEntity.getHeaders().getLocation());
        return responseEntity.getBody();
    }


    /*
     * Traverson
     */
    public Collection<Ingredient> allIngredients() {
        ParameterizedTypeReference<CollectionModel<Ingredient>> parameterizedTypeReference = new ParameterizedTypeReference<CollectionModel<Ingredient>>() {
        };
        CollectionModel<Ingredient> ingredients = traverson.follow("ingredients").toObject(parameterizedTypeReference);
        Collection<Ingredient> content = ingredients.getContent();
        return content;
    }

    public Collection<TacoModel> recent() {
        ParameterizedTypeReference<CollectionModel<TacoModel>> parameterizedTypeReference = new ParameterizedTypeReference<CollectionModel<TacoModel>>() {
        };
        CollectionModel<TacoModel> tacos = traverson.follow("tacos").follow("recent").toObject(parameterizedTypeReference);
        return tacos.getContent();
    }

    public Ingredient addIngredient(Ingredient ingredient) {
        String ingredientsUrl = traverson.follow("ingredients")
                .asLink()
                .getHref();
        return restTemplate.postForObject(ingredientsUrl, ingredient, Ingredient.class);
    }
}
