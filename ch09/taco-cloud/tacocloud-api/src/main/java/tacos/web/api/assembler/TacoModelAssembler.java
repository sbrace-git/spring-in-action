package tacos.web.api.assembler;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import tacos.model.Taco;
import tacos.web.api.model.TacoModel;

public class TacoModelAssembler implements RepresentationModelAssembler<Taco, TacoModel> {

    @Override
    public TacoModel toModel(Taco entity) {
        return new TacoModel(entity);
    }
}
