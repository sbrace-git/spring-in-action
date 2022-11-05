package tacos.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import tacos.model.Taco;

public interface TacoRepository extends PagingAndSortingRepository<Taco, Long> {

}
