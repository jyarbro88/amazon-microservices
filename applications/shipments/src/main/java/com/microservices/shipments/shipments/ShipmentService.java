package com.microservices.shipments.shipments;

import com.microservices.shipments.models.LineItemToDisplay;
import com.microservices.shipments.models.ShipmentToDisplay;
import com.microservices.shipments.models.TempLineItem;
import com.microservices.shipments.models.TempProduct;
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

    List<ShipmentToDisplay> getShipmentByAccountId(Long id) {

        List<LineItemToDisplay> lineItemsToDisplayList = new ArrayList<>();
        List<ShipmentToDisplay> shipmentToDisplayList = new ArrayList<>();

        List<Shipment> allShipmentsForAccount = shipmentRepository.findAllByAccountIdOrderByDeliveredDate(id);

        for (Shipment shipment : allShipmentsForAccount) {

            ShipmentToDisplay shipmentToDisplay = new ShipmentToDisplay();
            Map<Long, Integer> lineItemsMap = new HashMap<>();
            Map<Long, String> productsMap = new HashMap<>();

            shipmentToDisplay.setDeliveredDate(shipment.getDeliveredDate());
            shipmentToDisplay.setShipmentDate(shipment.getShippedDate());
            shipmentToDisplay.setOrderNumber(shipment.getOrderId());

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
                LineItemToDisplay lineItemToDisplay = new LineItemToDisplay();
                Integer lineItemQuantity = lineItemsMap.get(productId);
                String productName = productsMap.get(productId);
                lineItemToDisplay.setProductName(productName);
                lineItemToDisplay.setQuantity(lineItemQuantity);

                lineItemsToDisplayList.add(lineItemToDisplay);
            }

            shipmentToDisplay.setOrderLineItems(lineItemsToDisplayList);

            shipmentToDisplayList.add(shipmentToDisplay);
        }

        return shipmentToDisplayList;
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
}
