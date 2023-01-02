package com.example.ingredientservice;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/ingredients", produces = "application/json")
@CrossOrigin(origins = "*")
public class IngredientController {

    private IngredientRepository repo;

    @Autowired
    public IngredientController(IngredientRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    @HystrixCommand(fallbackMethod = "getDefaultIngredients")
    public Iterable<Ingredient> allIngredients() {
        int i = 1 / 0;
        return repo.findAll();
    }

    private Iterable<Ingredient> getDefaultIngredients() throws InterruptedException {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient(
                "FLTO", "Flour Tortilla - 2", Ingredient.Type.WRAP));
        ingredients.add(new Ingredient(
                "GRBF", "Ground Beef - 2", Ingredient.Type.PROTEIN));
        ingredients.add(new Ingredient(
                "CHED", "Shredded Cheddar - 2", Ingredient.Type.CHEESE));
        return ingredients;
    }

    @GetMapping("/{id}")
    public Optional<Ingredient> byId(@PathVariable String id) {
        return repo.findById(id);
    }

    @PutMapping("/{id}")
    public void updateIngredient(@PathVariable String id, @RequestBody Ingredient ingredient) {
        if (!ingredient.getId().equals(id)) {
            throw new IllegalStateException("Given ingredient's ID doesn't match the ID in the path.");
        }
        repo.save(ingredient);
    }

    @PostMapping
    public ResponseEntity<Ingredient> postIngredient(@RequestBody Ingredient ingredient) {
        Ingredient saved = repo.save(ingredient);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("http://localhost:8080/ingredients/" + ingredient.getId()));
        return new ResponseEntity<>(saved, headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteIngredient(@PathVariable String id) {
        repo.deleteById(id);
    }

}
