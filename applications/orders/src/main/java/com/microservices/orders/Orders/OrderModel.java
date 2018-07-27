package com.microservices.orders.Orders;

import com.microservices.orders.LineItems.LineItemModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "orders")
public class OrderModel {

    @Id
    @GeneratedValue
    private Long id;
    private Long accountId;
    private Date orderDate;
    private Long shippingAddressId;
    private Long billingAddressId;
    private Double totalPrice;

    @OneToMany
    private List<LineItemModel> lineItemModelList;

}
