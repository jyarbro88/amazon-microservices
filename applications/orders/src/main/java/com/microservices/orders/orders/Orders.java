package com.microservices.orders.orders;

import com.microservices.orders.lineItems.LineItems;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue
    private Long id;
    private Long accountId;
    private Date orderDate;
    private Long shippingAddressId;
    private Long billingAddressId;
    private Double totalPrice;

    @OneToMany
    private List<LineItems> lineItemsList;

}