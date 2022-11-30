package tacos.web.api.controller;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tacos.model.Ingredient;
import tacos.repository.IngredientRepository;

import java.util.List;

@RestController
@RequestMapping(path = "/ingredients", produces = "application/json")
@CrossOrigin(origins = "*")
public class IngredientApiController {

    private IngredientRepository ingredientRepository;

    public IngredientApiController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping
    public Flux<Ingredient> allIngredients() {
        Flux<Ingredient> all = ingredientRepository.findAll();
        List<Ingredient> block = all.collectList().block();
        return all;
    }

    @GetMapping("/{id}")
    public Mono<Ingredient> ingredientById(@PathVariable("id") String id) {
        return ingredientRepository.findById(id);
    }
}
