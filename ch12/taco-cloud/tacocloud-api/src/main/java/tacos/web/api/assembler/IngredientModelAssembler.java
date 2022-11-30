package tacos.web.api.assembler;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import tacos.model.Ingredient;
import tacos.web.api.model.IngredientModel;

public class IngredientModelAssembler implements RepresentationModelAssembler<Ingredient, IngredientModel> {

    @Override
    public IngredientModel toModel(Ingredient entity) {
        return new IngredientModel(entity);
    }
}
