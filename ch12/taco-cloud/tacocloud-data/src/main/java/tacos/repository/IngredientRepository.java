package tacos.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import tacos.model.Ingredient;

public interface IngredientRepository
        extends ReactiveCrudRepository<Ingredient, String> {

}
