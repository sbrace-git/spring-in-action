package tacos.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
public class Taco {

    private Long id;

    private Date createdAt;

    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    // TODO: 2022/10/27 前端？
    @NotEmpty(message = "You must choose at least 1 ingredient")
    private List<Ingredient> ingredients;
}
