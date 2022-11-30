package tacos.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import tacos.model.Taco;

public interface TacoRepository extends ReactiveCrudRepository<Taco, String> {

}
