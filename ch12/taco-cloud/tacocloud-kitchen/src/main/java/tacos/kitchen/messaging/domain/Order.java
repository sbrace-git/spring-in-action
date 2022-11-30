package tacos.kitchen.messaging.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Order {

    private Long id;


    private String deliveryName;

    private String deliveryStreet;

    private String deliveryCity;

    private String deliveryState;

    private String deliveryZip;

    private String ccNumber;

    private String ccExpiration;

    private String ccCVV;

    private Date placedAt;


    private List<Taco> tacos = new ArrayList<>();


}
