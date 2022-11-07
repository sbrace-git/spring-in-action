package tacos.web.api.model;

import lombok.Getter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import tacos.model.Taco;
import tacos.web.api.assembler.IngredientModelAssembler;
import tacos.web.api.controller.DesignTacoAipController;

import java.util.Date;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Getter
public class TacoModel extends RepresentationModel<TacoModel> {

    private static final IngredientModelAssembler ingredientModelAssembler = new IngredientModelAssembler();

    private Date createdAt;
    private String name;
    private CollectionModel<IngredientModel> ingredients;

    public TacoModel(Taco taco) {
        this.createdAt = taco.getCreatedAt();
        this.name = taco.getName();
        this.ingredients = ingredientModelAssembler.toCollectionModel(taco.getIngredients());
        add(linkTo(methodOn(DesignTacoAipController.class).tacoById(taco.getId())).withRel("href"));
    }
}
