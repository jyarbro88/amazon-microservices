package com.microservices.orders.orders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.orders.models.display.OrderShipmentsToDisplay;
import com.microservices.orders.repositories.LineItemRepository;
import com.microservices.orders.models.display.OrderLineItemToDisplay;
import com.microservices.orders.models.Order;
import com.microservices.orders.models.temp.TempProductObject;
import com.microservices.orders.models.display.OrderToDisplay;
import com.microservices.orders.models.LineItem;
import com.microservices.orders.models.display.OrderAddressToDisplay;
import com.microservices.orders.models.temp.TempShipmentObject;
import com.microservices.orders.repositories.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//Todo:  Create operations for new Order which links the address, account id when saving
//Todo:  Calculate Total Prices with utility class and not manually
//Todo:  Order Total Price is null
//Todo:  OrderShipmentToDisplay needs order line item id and order id
//Todo:  Link Account Address with Account when creating new, then remove from AccountApplication run file


@RestController
public class OrderController {

    private final OrderRepository orderRepository;
    private final LineItemRepository lineItemRepository;
    private final RestTemplate restTemplate;
//    private final MicroServiceInstances microServiceInstances;

    public OrderController(OrderRepository orderRepository, LineItemRepository lineItemRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.lineItemRepository = lineItemRepository;
        this.restTemplate = restTemplate;
//        this.microServiceInstances = microServiceInstances;
    }

    @GetMapping(value = "orders/accountLookup")
    public List<Order> getAllOrdersForAccountId(
            @RequestParam(value = "accountId") Long accountId
    ) {
        return orderRepository.findAllByAccountIdOrderByOrderDate(accountId);
    }

    @GetMapping(value = "orders/details/{id}")
    public OrderToDisplay getOrderDetailsForId(
            @PathVariable(value = "id") Long orderIdToSearch
    ) {
        Optional<Order> foundOrderList = orderRepository.findById(orderIdToSearch);
        Order foundOrder = foundOrderList.get();
        Long shippingAddressId = foundOrder.getShippingAddressId();

        OrderToDisplay orderToDisplay = new OrderToDisplay();
        orderToDisplay.setOrderNumber(foundOrder.getId());
        orderToDisplay.setOrderTotalPrice(foundOrder.getTotalPrice());

        OrderAddressToDisplay orderShippingAddress = restTemplate.getForObject("http://accounts-service/accounts/" + foundOrder.getAccountId() + "/accountAddresses/" + shippingAddressId, OrderAddressToDisplay.class);

        orderShippingAddress.setShippingAddressId(shippingAddressId);
        orderToDisplay.setShippingAddress(orderShippingAddress);

        List<LineItem> lineItemsForOrderId = lineItemRepository.findAllByOrderId(foundOrder.getId());

        List<OrderLineItemToDisplay> lineItemsForOrderList = new ArrayList<>();
        List<OrderShipmentsToDisplay> shipmentItemsForOrderList = new ArrayList<>();

        for (LineItem lineItem : lineItemsForOrderId) {
            OrderLineItemToDisplay orderLineItemToDisplay = new OrderLineItemToDisplay();
            OrderShipmentsToDisplay shipmentLineItemToDisplay = new OrderShipmentsToDisplay();

//            TempProductObject tempProduct = restTemplate.getForObject(microServiceInstances.getProductName(lineItem.getProductId()), TempProductObject.class);
//            TempShipmentObject tempShipment = restTemplate.getForObject(microServiceInstances.getShipmentForLineItemId(lineItem.getShipmentId()), TempShipmentObject.class);

            TempProductObject tempProduct = restTemplate.getForObject("http://products-service/products/" + lineItem.getProductId(), TempProductObject.class);
            TempShipmentObject tempShipment = restTemplate.getForObject("http://shipments-service/shipments/lineItems/" + lineItem.getShipmentId(), TempShipmentObject.class);

            shipmentLineItemToDisplay.setDeliveryDate(tempShipment.getDeliveredDate());
            shipmentLineItemToDisplay.setShippedDate(tempShipment.getShippedDate());
            shipmentLineItemToDisplay.setShipmentId(tempShipment.getId());
            shipmentLineItemToDisplay.setOrderLineItemId(lineItem.getId());

            orderLineItemToDisplay.setOrderLineItemId(lineItem.getId());
            orderLineItemToDisplay.setProductName(tempProduct.getName());
            orderLineItemToDisplay.setQuantity(lineItem.getQuantity());
//            lineItem.getOrderId();
            lineItemsForOrderList.add(orderLineItemToDisplay);
            shipmentItemsForOrderList.add(shipmentLineItemToDisplay);
        }

        orderToDisplay.setOrderLineItemsList(lineItemsForOrderList);
        orderToDisplay.setOrderShipmentsList(shipmentItemsForOrderList);

        return orderToDisplay;
    }

    @PostMapping(value = "orders/newOrder", consumes = "application/json")
    public ResponseEntity addNewOrder(
            @RequestBody String orderJson
    ) throws IOException {

        List<JsonNode> newOrderLineItems = new ArrayList<>();

//        ResponseEntity<String> response = restTemplate.getForEntity(stockKeyUrl, String.class);
        ObjectMapper mapper = new ObjectMapper();
//        JsonNode root = mapper.readTree(orderJson.get());

//        JsonNode lineItems = root.path("lineItems");
//        root.path("")
//
//        JsonNode companySymbol = root.path("symbol");
//        JsonNode companyName = root.path("companyName");

        return new ResponseEntity(HttpStatus.OK);
    }
}
