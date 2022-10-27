package tacos.repository.impl;

import org.springframework.stereotype.Repository;
import tacos.model.Order;
import tacos.repository.OrderRepository;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    @Override
    public Order save(Order order) {
        return null;
    }
}
