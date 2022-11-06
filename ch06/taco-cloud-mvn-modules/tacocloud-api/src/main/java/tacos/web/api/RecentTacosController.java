package tacos.web.api;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tacos.model.Taco;
import tacos.repository.TacoRepository;

import java.util.List;

@RepositoryRestController
public class RecentTacosController {
    private TacoRepository tacoRepository;

    public RecentTacosController(TacoRepository tacoRepository) {
        this.tacoRepository = tacoRepository;
    }

    @GetMapping(path = "/tacos/recent", produces = "application/hal+json")
    @ResponseBody
    public List<Taco> recentTacos() {
        PageRequest page = PageRequest.of(
                0, 12, Sort.by("createdAt").descending());
        return tacoRepository.findAll(page).getContent();
    }
}