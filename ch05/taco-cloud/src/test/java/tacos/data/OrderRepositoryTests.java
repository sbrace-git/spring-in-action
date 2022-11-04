package tacos.data;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import tacos.model.Ingredient;
import tacos.model.Ingredient.Type;
import tacos.model.Order;
import tacos.model.Taco;
import tacos.repository.OrderRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OrderRepositoryTests {

    @Autowired
    OrderRepository orderRepo;

    @Test
    @Disabled("TODO: Fix this to deal with security stuffs")
    public void saveOrderWithTwoTacos() {
        Order order = new Order();
        order.setDeliveryName("Test McTest");
        order.setDeliveryStreet("1234 Test Lane");
        order.setDeliveryCity("Testville");
        order.setDeliveryState("CO");
        order.setDeliveryZip("80123");
        order.setCcNumber("4111111111111111");
        order.setCcExpiration("10/23");
        order.setCcCVV("123");
        Taco taco1 = new Taco();
        taco1.setName("Taco One");
        List<Ingredient> ingredients1 = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                new Ingredient("CHED", "Shredded Cheddar", Type.CHEESE)
        );
        taco1.setIngredients(ingredients1);

        order.addTaco(taco1);
        Taco taco2 = new Taco();
        taco2.setName("Taco Two");
        List<Ingredient> ingredients2 = Arrays.asList(
                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE)
        );
        taco2.setIngredients(ingredients2);

        order.addTaco(taco2);

        Order savedOrder = orderRepo.save(order);
        assertThat(savedOrder.getId()).isNotNull();

        Order fetchedOrder = orderRepo.findById(savedOrder.getId()).get();
        assertThat(fetchedOrder.getDeliveryName()).isEqualTo("Test McTest");
        assertThat(fetchedOrder.getDeliveryStreet()).isEqualTo("1234 Test Lane");
        assertThat(fetchedOrder.getDeliveryCity()).isEqualTo("Testville");
        assertThat(fetchedOrder.getDeliveryState()).isEqualTo("CO");
        assertThat(fetchedOrder.getDeliveryZip()).isEqualTo("80123");
        assertThat(fetchedOrder.getCcNumber()).isEqualTo("4111111111111111");
        assertThat(fetchedOrder.getCcExpiration()).isEqualTo("10/23");
        assertThat(fetchedOrder.getCcCVV()).isEqualTo("123");
        assertThat(fetchedOrder.getPlacedAt().getTime()).isEqualTo(savedOrder.getPlacedAt().getTime());
        List<Taco> tacos = fetchedOrder.getTacos();
        assertThat(tacos.size()).isEqualTo(2);
        assertThat(tacos).containsExactlyInAnyOrder(taco1, taco2);
    }

}
