package com.microservices.shipments.services;

import com.microservices.shipments.circuits.OrderCircuitBreaker;
import com.microservices.shipments.models.Shipment;
import com.microservices.shipments.models.display.DisplayLineItem;
import com.microservices.shipments.models.display.DisplayShipment;
import com.microservices.shipments.models.temp.TempLineItem;
import com.microservices.shipments.models.temp.TempProduct;
import com.microservices.shipments.shipments.ShipmentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountService {

    private ShipmentRepository shipmentRepository;
    private OrderCircuitBreaker orderCircuitBreaker;

    public AccountService(ShipmentRepository shipmentRepository, OrderCircuitBreaker orderCircuitBreaker) {
        this.shipmentRepository = shipmentRepository;
        this.orderCircuitBreaker = orderCircuitBreaker;
    }

    public List<DisplayShipment> getShipmentByAccountId(Long id) {

        List<DisplayLineItem> lineItemsToDisplay = new ArrayList<>();
        List<DisplayShipment> shipmentsToDisplay = new ArrayList<>();

        List<Shipment> allShipmentsForAccount = shipmentRepository.findAllByAccountIdOrderByDeliveredDate(id);

        for (Shipment shipment : allShipmentsForAccount) {
            Long orderId = shipment.getOrderId();

            DisplayShipment displayShipment = buildDisplayShipmentModel(shipment);
            Map<Long, Integer> lineItemsMap = getTempLineItem(orderId);
            Map<Long, String> productsMap = getTempProduct(orderId);

            setProductNameAndQuantity(lineItemsToDisplay, lineItemsMap, productsMap);

            displayShipment.setOrderDisplayLineItems(lineItemsToDisplay);

            shipmentsToDisplay.add(displayShipment);
        }

        return shipmentsToDisplay;
    }

    private void setProductNameAndQuantity(List<DisplayLineItem> displayLineItemsToDisplayList, Map<Long, Integer> lineItemsMap, Map<Long, String> productsMap) {
        for (Long productId : productsMap.keySet()) {
            DisplayLineItem displayLineItem = new DisplayLineItem();
            Integer lineItemQuantity = lineItemsMap.get(productId);
            String productName = productsMap.get(productId);
            displayLineItem.setProductName(productName);
            displayLineItem.setQuantity(lineItemQuantity);

            displayLineItemsToDisplayList.add(displayLineItem);
        }
    }

    private Map<Long, String> getTempProduct(Long orderId) {

        Map<Long, String> productsMap = new HashMap<>();
        TempProduct[] tempProduct = orderCircuitBreaker.getTempProductInfoForOrder(orderId);
        for (TempProduct product : tempProduct) {
            Long productId = product.getId();
            String name = product.getName();

            productsMap.put(productId, name);
        }

        return productsMap;
    }

    private Map<Long, Integer> getTempLineItem(Long orderId) {

        Map<Long, Integer> lineItemsMap = new HashMap<>();
        TempLineItem[] tempLineItem = orderCircuitBreaker.getTempLineItemsForOrder(orderId);
        for (TempLineItem lineItem : tempLineItem) {
            Long lineItemProductId = lineItem.getProductId();
            Integer lineItemQuantity = lineItem.getQuantity();

            lineItemsMap.put(lineItemProductId, lineItemQuantity);
        }

        return lineItemsMap;
    }

    private DisplayShipment buildDisplayShipmentModel(Shipment shipment) {
        DisplayShipment displayShipment = new DisplayShipment();
        displayShipment.setDeliveredDate(shipment.getDeliveredDate());
        displayShipment.setShipmentDate(shipment.getShippedDate());
        displayShipment.setOrderNumber(shipment.getOrderId());
        return displayShipment;
    }
}
