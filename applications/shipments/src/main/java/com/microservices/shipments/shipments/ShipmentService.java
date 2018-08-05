package com.microservices.shipments.shipments;

import com.microservices.shipments.models.display.DisplayLineItem;
import com.microservices.shipments.models.display.DisplayShipment;
import com.microservices.shipments.models.temp.TempLineItem;
import com.microservices.shipments.models.temp.TempProduct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ShipmentService {

    private ShipmentRepository shipmentRepository;
    private RestTemplate restTemplate;

    public ShipmentService(ShipmentRepository shipmentRepository, RestTemplate restTemplate) {
        this.shipmentRepository = shipmentRepository;
        this.restTemplate = restTemplate;
    }

    Iterable<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }

    Shipment createNewShipment(Shipment shipment) {
        return shipmentRepository.save(shipment);
    }

    void deleteShipment(Long id) {
        shipmentRepository.deleteById(id);
    }

    Optional<Shipment> getShipmentById(Long id) {
        return shipmentRepository.findById(id);
    }

    List<DisplayShipment> getShipmentByAccountId(Long id) {

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

            //Todo:  Replace rest calls with circuit breakers
            TempProduct[] tempProduct = restTemplate.getForObject("http://orders-service/orders/getProductInfo/" + orderId, TempProduct[].class);
            TempLineItem[] tempLineItem = restTemplate.getForObject("http://orders-service/lineItems/" + orderId + "/lookup", TempLineItem[].class);

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

    Shipment updateShipment(Shipment shipment) {
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

    ResponseEntity updateShippingDate(Long orderId, Shipment passedInShipment) {

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
