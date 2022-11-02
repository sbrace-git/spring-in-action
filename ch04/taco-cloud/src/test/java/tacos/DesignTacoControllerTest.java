package tacos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import tacos.model.Ingredient;
import tacos.model.Taco;
import tacos.repository.IngredientRepository;
import tacos.repository.TacoRepository;
import tacos.web.DesignTacoController;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static tacos.model.Ingredient.Type;

@WebMvcTest(DesignTacoController.class)
public class DesignTacoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientRepository ingredientRepository;

    @MockBean
    private TacoRepository tacoRepository;

    private static List<Ingredient> ingredients;

    @BeforeEach
    public void setup() {
        ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
        );

        when(ingredientRepository.findAll()).thenReturn(ingredients);

        when(ingredientRepository.findById("FLTO")).thenReturn(Optional.of(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP)));
        when(ingredientRepository.findById("GRBF")).thenReturn(Optional.of(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN)));
        when(ingredientRepository.findById("CHED")).thenReturn(Optional.of(new Ingredient("CHED", "Cheddar", Type.CHEESE)));

    }


    @Test
    @WithMockUser
    public void testShowDesignForm() throws Exception {
        mockMvc.perform(get("/design"))
                .andExpect(status().isOk())
                .andExpect(view().name("design"))
                .andExpect(model().attribute("wrap", ingredients.subList(0, 2)))
                .andExpect(model().attribute("protein", ingredients.subList(2, 4)))
                .andExpect(model().attribute("veggies", ingredients.subList(4, 6)))
                .andExpect(model().attribute("cheese", ingredients.subList(6, 8)))
                .andExpect(model().attribute("sauce", ingredients.subList(8, 10)));
    }

    @Test
    public void processDesign() throws Exception {
        mockMvc.perform(post("/design")
                        .content("name=Test+Taco&ingredients=FLTO,GRBF,CHED")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().stringValues("Location", "/orders/current"));
    }

    @Test
    public void processDesign_shortName() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/design")
                        .content("name=Test&ingredients=FLTO,GRBF,CHED")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("design"))
                .andExpect(model().attributeExists("taco"))
                .andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertNotNull(modelAndView);
        ModelMap modelMap = modelAndView.getModelMap();
        Object tacoObject = modelMap.getAttribute("taco");
        assertNotNull(tacoObject);
        assertInstanceOf(Taco.class, tacoObject);
        Taco taco = (Taco) tacoObject;
        String name = taco.getName();
        assertEquals(name, "Test");
        List<Ingredient> ingredients = taco.getIngredients();
        assertEquals(ingredients, Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                new Ingredient("CHED", "Cheddar", Type.CHEESE)
        ));
    }

}
