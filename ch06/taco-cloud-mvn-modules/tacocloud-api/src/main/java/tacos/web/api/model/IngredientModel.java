package tacos.web.api.model;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import tacos.model.Ingredient;
import tacos.model.Ingredient.Type;
import tacos.web.api.controller.IngredientApiController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Getter
public class IngredientModel extends RepresentationModel<IngredientModel> {
    private String name;
    private Type type;

    public IngredientModel(Ingredient ingredient) {
        this.name = ingredient.getName();
        this.type = ingredient.getType();
        add(linkTo(methodOn(IngredientApiController.class).ingredientById(ingredient.getId())).withRel("self"));
    }
}
