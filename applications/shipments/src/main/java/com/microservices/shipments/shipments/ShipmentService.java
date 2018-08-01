package com.microservices.shipments.shipments;

import com.microservices.shipments.models.LineItemToDisplay;
import com.microservices.shipments.models.ShipmentToDisplay;
import com.microservices.shipments.models.TempProduct;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShipmentService {

    private ShipmentRepository shipmentRepository;
    private RestTemplate restTemplate;

    public ShipmentService(ShipmentRepository shipmentRepository, RestTemplate restTemplate) {
        this.shipmentRepository = shipmentRepository;
        this.restTemplate = restTemplate;
    }

    public Iterable<Shipment> getAllShipments(){
        return shipmentRepository.findAll();
    }

    public Optional<Shipment> getShipmentById(Long id){
        return shipmentRepository.findById(id);
    }

    public List<ShipmentToDisplay> getShipmentByAccountId(Long id){

        List<Shipment> allByAccountIdOrderByDeliveredDate = shipmentRepository.findAllByAccountIdOrderByDeliveredDate(id);
        List<LineItemToDisplay> lineItemsList = new ArrayList<>();
        List<ShipmentToDisplay> shipmentToDisplayList = new ArrayList<>();

        for (Shipment shipment : allByAccountIdOrderByDeliveredDate) {

            ShipmentToDisplay shipmentToDisplay = new ShipmentToDisplay();
            LineItemToDisplay lineItemToDisplay = new LineItemToDisplay();

            shipmentToDisplay.setDeliveredDate(shipment.getDeliveredDate());
            shipmentToDisplay.setShipmentDate(shipment.getShippedDate());
            shipmentToDisplay.setOrderNumber(shipment.getOrderId());
            Long lineItemId = shipment.getLineItemId();

            TempProduct tempProduct = restTemplate.getForObject("http://orders-service/orders/getProductInfo/" + lineItemId, TempProduct.class);

            lineItemToDisplay.setProductName(tempProduct.getName());
            lineItemToDisplay.setQuantity(tempProduct.getQuantity());

            lineItemsList.add(lineItemToDisplay);

            shipmentToDisplay.setOrderLineItems(lineItemsList);

            shipmentToDisplayList.add(shipmentToDisplay);
        }

        return shipmentToDisplayList;
    }

    public Shipment createNewShipment(Shipment shipment){
        return shipmentRepository.save(shipment);
    }

    public Shipment updateShipment(Shipment shipment){
        Optional<Shipment> byId = shipmentRepository.findById(shipment.getId());
        Shipment foundShipment = byId.get();
        foundShipment.setAccountId(shipment.getAccountId());
        foundShipment.setLineItemId(shipment.getLineItemId());
        foundShipment.setDeliveredDate(shipment.getDeliveredDate());
        foundShipment.setOrderId(shipment.getOrderId());
        foundShipment.setShippedDate(shipment.getShippedDate());
        foundShipment.setShippingAddressId(shipment.getShippingAddressId());

        return shipmentRepository.save(foundShipment);
    }

    public void deleteShipment(Long id){
        shipmentRepository.deleteById(id);
    }
}
