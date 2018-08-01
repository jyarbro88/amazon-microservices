package com.microservices.orders.controllers;

import com.microservices.orders.models.Order;
import com.microservices.orders.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Todo:  Create operations for new Order which links the address, account id when saving
//Todo:  Calculate Total Prices with utility class and not manually
//Todo:  Order Total Price is null
//Todo:  OrderShipmentToDisplay needs order line item id and order id
//Todo:  Link Account Address with Account when creating new, then remove from AccountApplication run file


@RestController
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    //    private final OrderRepository orderRepository;
//    private final LineItemRepository lineItemRepository;
//    private final RestTemplate restTemplate;
//    private final MicroServiceInstances microServiceInstances;

//    public OrderController(OrderRepository orderRepository, LineItemRepository lineItemRepository, RestTemplate restTemplate) {
//        this.orderRepository = orderRepository;
//        this.lineItemRepository = lineItemRepository;
//        this.restTemplate = restTemplate;
//        this.microServiceInstances = microServiceInstances;
//    }

    @GetMapping(value = "/orders")
    public Iterable<Order> getAllOrders(){
        return orderService.getAll();
    }

    @GetMapping(value = "/orders/accountLookup")
    public List<Order> getAllOrdersForAccountId(
            @RequestParam(value = "accountId") Long accountId
    ) {
        return orderService.getAllOrdersForIdOrderByDate(accountId);
    }

    @PostMapping(value = "/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public Order createNewOrder(
            @RequestBody Order order
    ){
        return orderService.createNewOrder(order);
    }

    @PutMapping(value = "/orders/update")
    @ResponseStatus(HttpStatus.CREATED)
    public Order updateOrder(
            @RequestBody Order order
    ){
        return orderService.updateOrder(order);
    }

    @DeleteMapping(value = "/orders/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable(value = "id") Long orderId){
        orderService.deleteOrder(orderId);
    }
}







//    @GetMapping(value = "orders/details/{id}")
//    public OrderToDisplay getOrderDetailsForId(
//            @PathVariable(value = "id") Long orderIdToSearch
//    ) {
//        Optional<Order> foundOrderList = orderRepository.findById(orderIdToSearch);
//        Order foundOrder = foundOrderList.get();
//        Long shippingAddressId = foundOrder.getShippingAddressId();
//
//        OrderToDisplay orderToDisplay = new OrderToDisplay();
//        orderToDisplay.setOrderNumber(foundOrder.getId());
//        orderToDisplay.setOrderTotalPrice(foundOrder.getTotalPrice());
//
//        OrderAddressToDisplay orderShippingAddress = restTemplate.getForObject("http://accounts-service/accounts/" + foundOrder.getAccountId() + "/accountAddresses/" + shippingAddressId, OrderAddressToDisplay.class);
//
//        orderShippingAddress.setShippingAddressId(shippingAddressId);
//        orderToDisplay.setShippingAddress(orderShippingAddress);
//
//        List<LineItem> lineItemsForOrderId = lineItemRepository.findAllByOrderId(foundOrder.getId());
//
//        List<OrderLineItemToDisplay> lineItemsForOrderList = new ArrayList<>();
//        List<OrderShipmentsToDisplay> shipmentItemsForOrderList = new ArrayList<>();
//
//        for (LineItem lineItem : lineItemsForOrderId) {
//            OrderLineItemToDisplay orderLineItemToDisplay = new OrderLineItemToDisplay();
//            OrderShipmentsToDisplay shipmentLineItemToDisplay = new OrderShipmentsToDisplay();

//            TempProductObject tempProduct = restTemplate.getForObject(microServiceInstances.getProductName(lineItem.getProductId()), TempProductObject.class);
//            TempShipmentObject tempShipment = restTemplate.getForObject(microServiceInstances.getShipmentForLineItemId(lineItem.getShipmentId()), TempShipmentObject.class);

//            TempProductObject tempProduct = restTemplate.getForObject("http://products-service/products/" + lineItem.getProductId(), TempProductObject.class);
//            TempShipmentObject tempShipment = restTemplate.getForObject("http://shipments-service/shipments/lineItems/" + lineItem.getShipmentId(), TempShipmentObject.class);
//
//            shipmentLineItemToDisplay.setDeliveryDate(tempShipment.getDeliveredDate());
//            shipmentLineItemToDisplay.setShippedDate(tempShipment.getShippedDate());
//            shipmentLineItemToDisplay.setShipmentId(tempShipment.getId());
//            shipmentLineItemToDisplay.setOrderLineItemId(lineItem.getId());
//
//            orderLineItemToDisplay.setOrderLineItemId(lineItem.getId());
//            orderLineItemToDisplay.setProductName(tempProduct.getName());
//            orderLineItemToDisplay.setQuantity(lineItem.getQuantity());
//            lineItem.getOrderId();
//            lineItemsForOrderList.add(orderLineItemToDisplay);
//            shipmentItemsForOrderList.add(shipmentLineItemToDisplay);
//        }
//
//        orderToDisplay.setOrderLineItemsList(lineItemsForOrderList);
//        orderToDisplay.setOrderShipmentsList(shipmentItemsForOrderList);
//
//        return orderToDisplay;
//    }