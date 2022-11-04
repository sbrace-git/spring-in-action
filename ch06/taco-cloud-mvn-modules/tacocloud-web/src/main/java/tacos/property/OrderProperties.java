package tacos.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Validated
@Component
@ConfigurationProperties(prefix = "taco.orders")
public class OrderProperties {

    @Min(value = 5,message = "must be between 5 to 25")
    @Max(value = 25,message = "must be between 5 to 25")
    private int pageSize = 20;
}
