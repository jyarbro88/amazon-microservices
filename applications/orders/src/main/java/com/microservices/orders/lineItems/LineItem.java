package com.microservices.orders.lineItems;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "order_line_items")
public class LineItem {

    @Id
    @GeneratedValue
    private Long id;
    private Long productId;
    private Long shipmentId;
    private Long orderId;
    private Integer quantity;
    private Double singleItemPrice;
    private Double lineItemTotalPrice;
    private Boolean shipped;
    private Boolean delivered;

    public LineItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getSingleItemPrice() {
        return singleItemPrice;
    }

    public void setSingleItemPrice(Double singleItemPrice) {
        this.singleItemPrice = singleItemPrice;
    }

    public Double getLineItemTotalPrice() {
        return lineItemTotalPrice;
    }

    public void setLineItemTotalPrice(Double lineItemTotalPrice) {
        this.lineItemTotalPrice = lineItemTotalPrice;
    }

    public Boolean getShipped() {
        return shipped;
    }

    public void setShipped(Boolean shipped) {
        this.shipped = shipped;
    }

    public Boolean getDelivered() {
        return delivered;
    }

    public void setDelivered(Boolean delivered) {
        this.delivered = delivered;
    }
}
