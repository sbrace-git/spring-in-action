package tacos.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Taco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "CREATEDAT")
    private Date createdAt;

    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    @OneToMany(targetEntity = Ingredient.class)
    @JoinTable(name = "Taco_Ingredients",
            joinColumns = @JoinColumn(name = "taco", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient", referencedColumnName = "id"))
    @NotEmpty(message = "You must choose at least 1 ingredient")
    private List<Ingredient> ingredients;

    @PrePersist
    void createdAt() {
        createdAt = new Date();
    }
}
