package com.example.ingredientclient.info;

import com.example.ingredientclient.resttemplate.IngredientServiceClient;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TacoInfoContributor implements InfoContributor {

    private IngredientServiceClient client;

    public TacoInfoContributor(IngredientServiceClient client) {
        this.client = client;
    }

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Object> info = new HashMap<>();
        info.put("name", "name");
        info.put("key", "value");
        Long count = client.count();
        info.put("count", count);
        builder.withDetails(info);
    }
}
