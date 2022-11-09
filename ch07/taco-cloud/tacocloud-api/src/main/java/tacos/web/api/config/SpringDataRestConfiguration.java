package tacos.web.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import tacos.model.Ingredient;
import tacos.model.Taco;

import java.net.URI;

@Slf4j
@Configuration
public class SpringDataRestConfiguration {
    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {
        return new RepositoryRestConfigurer() {
            @Override
            public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
                config.exposeIdsFor(Ingredient.class);
            }
        };
    }

    @Bean
    public RepresentationModelProcessor<PagedModel<EntityModel<Taco>>>
    tacoProcessor(EntityLinks links) {

        return new RepresentationModelProcessor<PagedModel<EntityModel<Taco>>>() {
            @Override
            public PagedModel<EntityModel<Taco>> process(
                    PagedModel<EntityModel<Taco>> resource) {
                resource.add(
                        links.linkFor(Taco.class)
                                .slash("recent")
                                .withRel("recents"));
                return resource;
            }
        };
    }
}
