package tacos.repository;

import org.springframework.data.repository.CrudRepository;
import reactor.core.publisher.Mono;
import tacos.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

    Mono<User> findByUsername(String username);
}
