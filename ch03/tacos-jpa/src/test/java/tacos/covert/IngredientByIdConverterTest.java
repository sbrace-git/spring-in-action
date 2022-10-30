package tacos.covert;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tacos.converter.IngredientByIdConverter;
import tacos.model.Ingredient;
import tacos.repository.IngredientRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static tacos.model.Ingredient.Type;

public class IngredientByIdConverterTest {

    private IngredientByIdConverter ingredientByIdConverter;

    @BeforeEach
    public void setup() {
        IngredientRepository ingredientRepository = mock(IngredientRepository.class);
        when(ingredientRepository.findOne("AAAA"))
                .thenReturn(new Ingredient("AAAA", "TEST INGREDIENT", Type.CHEESE));
        when(ingredientRepository.findOne("ZZZZ"))
                .thenReturn(null);

        ingredientByIdConverter = new IngredientByIdConverter(ingredientRepository);
    }

    @Test
    public void shouldReturnValueWhenPresent() {

        assertThat(ingredientByIdConverter.convert("AAAA"))
                .isEqualTo(new Ingredient("AAAA", "TEST INGREDIENT", Type.CHEESE));
    }

    @Test
    public void shouldReturnNullWhenMissing() {
        assertThat(ingredientByIdConverter.convert("ZZZZ")).isNull();
    }
}
