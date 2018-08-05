package com.microservices.orders.services.Order;

import com.microservices.orders.models.LineItem;
import com.microservices.orders.models.Order;
import com.microservices.orders.models.temp.TempShipment;
import com.microservices.orders.services.CalculateUtil;
import com.microservices.orders.services.LineItem.NewLineItem;
import com.microservices.orders.services.LineItem.UpdateLineItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewOrder {

    private NewLineItem newLineItem;
    private OrderService orderService;
    private UpdateLineItem updateLineItem;
    private ShipmentService shipmentService;
    private CalculateUtil calculateUtil;

    public NewOrder(NewLineItem newLineItem, OrderService orderService, UpdateLineItem updateLineItem, ShipmentService shipmentService, CalculateUtil calculateUtil) {
        this.newLineItem = newLineItem;
        this.orderService = orderService;
        this.updateLineItem = updateLineItem;
        this.shipmentService = shipmentService;
        this.calculateUtil = calculateUtil;
    }

    public Order postNew(Order passedInOrder) {

        if (passedInOrder.getLineItems() != null) {
            for (LineItem lineItem: passedInOrder.getLineItems()) {
                newLineItem.postNew(lineItem);
            }

            Order order = orderService.saveOrder(passedInOrder);

            TempShipment shipment = shipmentService.buildAndPostNewShipment(order);

            Long orderId = order.getId();
            List<LineItem> orderLineItems = order.getLineItems();
            Long shipmentId = shipment.getId();

            for (LineItem lineItem : orderLineItems) {
                lineItem.setShipmentId(shipmentId);
                lineItem.setOrderId(orderId);

                updateLineItem.update(lineItem);
            }

            Double orderTotal = calculateUtil.orderTotalPrice(order);
            order.setTotalPrice(orderTotal);

            orderService.saveOrder(order);

            return order;

        } else {
            return orderService.saveOrder(passedInOrder);
        }
    }
}
