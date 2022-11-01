package tacos.model;

import lombok.Data;
import org.hibernate.annotations.Columns;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Taco_Order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Columns(columns = @Column(name = "DELIVERYNAME"))
    @NotBlank(message = "Delivery name is required")
    private String deliveryName;

    @Columns(columns = @Column(name = "DELIVERYSTREET"))
    @NotBlank(message = "Street is required")
    private String deliveryStreet;

    @Columns(columns = @Column(name = "DELIVERYCITY"))
    @NotBlank(message = "City is required")
    private String deliveryCity;

    @Columns(columns = @Column(name = "DELIVERYSTATE"))
    @NotBlank(message = "State is required")
    private String deliveryState;

    @Columns(columns = @Column(name = "DELIVERYZIP"))
    @NotBlank(message = "Zip code is required")
    private String deliveryZip;

    @Columns(columns = @Column(name = "CCNUMBER"))
    @CreditCardNumber(message = "Not a valid credit card number")
    private String ccNumber;

    @Columns(columns = @Column(name = "CCEXPIRATION"))
    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$", message = "Must be formatted MM/YY")
    private String ccExpiration;

    @Columns(columns = @Column(name = "CCCVV"))
    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    private String ccCVV;

    @Columns(columns = @Column(name = "PLACEDAT"))
    private Date placedAt;

    @PrePersist
    void placedAt() {
        placedAt = new Date();
    }

    @ManyToMany(targetEntity = Taco.class)
    @JoinTable(name = "Taco_Order_Tacos",
            joinColumns = @JoinColumn(name = "TACOORDER", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "TACO", referencedColumnName = "id")
    )
    private List<Taco> tacos = new ArrayList<>();

    @ManyToOne
    private User user;

    public boolean addTaco(Taco taco) {
        return tacos.add(taco);
    }
}
