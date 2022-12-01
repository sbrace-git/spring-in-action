package tacos.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import tacos.model.Order;
import tacos.model.User;

public interface OrderRepository extends ReactiveMongoRepository<Order, String> {

//    List<Order> findByDeliveryZip(String deliveryZip);

    Flux<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}
