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

    private NewLineItem newLineItemService;
    private OrderService orderService;
    private UpdateLineItem updateLineItemService;
    private ShipmentService shipmentService;
    private CalculateUtil calculateUtil;

    public NewOrder(NewLineItem newLineItemService, OrderService orderService, UpdateLineItem updateLineItemService, ShipmentService shipmentService, CalculateUtil calculateUtil) {
        this.newLineItemService = newLineItemService;
        this.orderService = orderService;
        this.updateLineItemService = updateLineItemService;
        this.shipmentService = shipmentService;
        this.calculateUtil = calculateUtil;
    }

    public Order postNew(Order passedInOrder) {

        if (passedInOrder.getLineItems() != null) {

            for (LineItem lineItem: passedInOrder.getLineItems()) {
                newLineItemService.postNew(lineItem);
            }

            Order order = orderService.save(passedInOrder);
            Long orderId = order.getId();

            TempShipment shipment = shipmentService.buildAndPostNewShipment(order);
            Long shipmentId = shipment.getId();

            List<LineItem> orderLineItems = order.getLineItems();
            for (LineItem lineItem : orderLineItems) {
                lineItem.setShipmentId(shipmentId);
                lineItem.setOrderId(orderId);
                updateLineItemService.update(lineItem);
            }

            Double orderTotal = calculateUtil.orderTotalPrice(order);
            order.setTotalPrice(orderTotal);

            orderService.save(order);

            return order;

        } else {
            return orderService.save(passedInOrder);
        }
    }
}
