package tacos.repository;

import tacos.model.Ingredient;

import java.util.List;

public interface IngredientRepository {
    List<Ingredient> findAll();

    Ingredient findOne(String id);

    Ingredient save(Ingredient ingredient);
}
