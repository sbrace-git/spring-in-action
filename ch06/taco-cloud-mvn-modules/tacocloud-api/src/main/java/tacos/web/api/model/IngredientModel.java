package tacos.web.api.model;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import tacos.model.Ingredient;
import tacos.model.Ingredient.Type;

@Getter
public class IngredientModel extends RepresentationModel<IngredientModel> {
    private String name;
    private Type type;

    public IngredientModel(Ingredient ingredient) {
        this.name = ingredient.getName();
        this.type = ingredient.getType();
    }
}
