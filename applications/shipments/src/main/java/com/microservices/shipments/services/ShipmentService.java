package com.microservices.shipments.services;

import com.microservices.shipments.circuits.OrderCircuitBreaker;
import com.microservices.shipments.models.Shipment;
import com.microservices.shipments.models.display.DisplayLineItem;
import com.microservices.shipments.models.display.DisplayShipment;
import com.microservices.shipments.models.temp.TempLineItem;
import com.microservices.shipments.models.temp.TempProduct;
import com.microservices.shipments.shipments.ShipmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ShipmentService {

    private ShipmentRepository shipmentRepository;
    private OrderCircuitBreaker orderCircuitBreaker;

    public ShipmentService(ShipmentRepository shipmentRepository, OrderCircuitBreaker orderCircuitBreaker) {
        this.shipmentRepository = shipmentRepository;
        this.orderCircuitBreaker = orderCircuitBreaker;
    }

    public Iterable<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }

    public Shipment createNewShipment(Shipment shipment) {
        return shipmentRepository.save(shipment);
    }

    public void deleteShipment(Long id) {
        shipmentRepository.deleteById(id);
    }

    public Optional<Shipment> getShipmentById(Long id) {
        return shipmentRepository.findById(id);
    }

    //Todo:  each method should be extracted out to their own classes
    public List<DisplayShipment> getShipmentByAccountId(Long id) {

        List<DisplayLineItem> displayLineItemsToDisplayList = new ArrayList<>();
        List<DisplayShipment> displayShipmentList = new ArrayList<>();

        List<Shipment> allShipmentsForAccount = shipmentRepository.findAllByAccountIdOrderByDeliveredDate(id);

        for (Shipment shipment : allShipmentsForAccount) {

            DisplayShipment displayShipment = new DisplayShipment();
            Map<Long, Integer> lineItemsMap = new HashMap<>();
            Map<Long, String> productsMap = new HashMap<>();

            displayShipment.setDeliveredDate(shipment.getDeliveredDate());
            displayShipment.setShipmentDate(shipment.getShippedDate());
            displayShipment.setOrderNumber(shipment.getOrderId());

            Long orderId = shipment.getOrderId();

            //Todo:  Replace rest calls with circuit circuits
            TempProduct[] tempProduct = orderCircuitBreaker.getTempProductInfoForOrder(orderId);
            TempLineItem[] tempLineItem = orderCircuitBreaker.getTempLineItemsForOrder(orderId);


            for (TempLineItem lineItem : tempLineItem) {
                Long lineItemProductId = lineItem.getProductId();
                Integer lineItemQuantity = lineItem.getQuantity();

                lineItemsMap.put(lineItemProductId, lineItemQuantity);
            }

            for (TempProduct product : tempProduct) {
                Long productId = product.getId();
                String name = product.getName();

                productsMap.put(productId, name);
            }

            for (Long productId : productsMap.keySet()) {
                DisplayLineItem displayLineItem = new DisplayLineItem();
                Integer lineItemQuantity = lineItemsMap.get(productId);
                String productName = productsMap.get(productId);
                displayLineItem.setProductName(productName);
                displayLineItem.setQuantity(lineItemQuantity);

                displayLineItemsToDisplayList.add(displayLineItem);
            }

            displayShipment.setOrderDisplayLineItems(displayLineItemsToDisplayList);

            displayShipmentList.add(displayShipment);
        }

        return displayShipmentList;
    }

    //Todo:  each method should be extracted out to their own classes
    public Shipment updateShipment(Shipment shipment) {
        Optional<Shipment> byId = shipmentRepository.findById(shipment.getId());
        Shipment foundShipment = byId.get();

        if (shipment.getAccountId() != null) {
            foundShipment.setAccountId(shipment.getAccountId());
        }
        if (shipment.getDeliveredDate() != null) {
            foundShipment.setDeliveredDate(shipment.getDeliveredDate());
        }
        if (shipment.getOrderId() != null) {
            foundShipment.setOrderId(shipment.getOrderId());
        }
        if (shipment.getShippedDate() != null) {
            foundShipment.setShippedDate(shipment.getShippedDate());
        }
        if (shipment.getOrderId() != null) {
            foundShipment.setOrderId(shipment.getOrderId());
        }
        if (shipment.getShippingAddressId() != null) {
            foundShipment.setShippingAddressId(shipment.getShippingAddressId());
        }

        return shipmentRepository.save(foundShipment);
    }

    public ResponseEntity updateShippingDate(Long orderId, Shipment passedInShipment) {

        List<Shipment> allByOrderId = shipmentRepository.findAllByOrderId(orderId);
        for (Shipment shipment : allByOrderId) {
            if (passedInShipment.getShippedDate() != null) {
                shipment.setShippedDate(passedInShipment.getShippedDate());
            }
            if (passedInShipment.getDeliveredDate() != null) {
                shipment.setDeliveredDate(passedInShipment.getDeliveredDate());
            }
            shipmentRepository.save(shipment);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

}
