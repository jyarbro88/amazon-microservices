package com.microservices.orders.orders;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.microservices.orders.lineItems.LineItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long accountId;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "MM-dd-yyyy")
    private Date orderDate;
    private Long shippingAddressId;
    private Long billingAddressId;
    private Double totalPrice;

    public Order(Long accountId, Date orderDate, Long shippingAddressId, Long billingAddressId, Double totalPrice) {
        this.accountId = accountId;
        this.orderDate = orderDate;
        this.shippingAddressId = shippingAddressId;
        this.billingAddressId = billingAddressId;
        this.totalPrice = totalPrice;
    }
}
