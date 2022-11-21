package tacos.web.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import tacos.model.Taco;
import tacos.web.api.assembler.IngredientModelAssembler;
import tacos.web.api.controller.DesignTacoAipController;

import java.util.Date;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = {"createdAt", "name", "ingredients"})
public class TacoModel extends RepresentationModel<TacoModel> {

    private static final IngredientModelAssembler ingredientModelAssembler = new IngredientModelAssembler();

    private Date createdAt;
    private String name;
    private CollectionModel<IngredientModel> ingredients;

    public TacoModel(Taco taco) {
        this.createdAt = taco.getCreatedAt();
        this.name = taco.getName();
        this.ingredients = ingredientModelAssembler.toCollectionModel(taco.getIngredients());
        add(linkTo(methodOn(DesignTacoAipController.class).tacoById(taco.getId())).withRel("self"));
    }
}
