package tacos.repository;

import org.springframework.data.repository.CrudRepository;
import tacos.model.Order;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> findByDeliveryZip(String deliveryZip);
}
