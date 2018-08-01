package com.microservices.orders.models.display;

import com.microservices.orders.models.display.OrderAddressToDisplay;
import com.microservices.orders.models.display.OrderLineItemToDisplay;
import com.microservices.orders.models.display.OrderShipmentsToDisplay;
import com.microservices.orders.models.display.OrderToDisplay;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class OrderToDisplayTest {

    private final OrderToDisplay testOrderItem = new OrderToDisplay();

    @Test
    public void setOrderNumber() throws NoSuchFieldException, IllegalAccessException {
        final Field field = testOrderItem.getClass().getDeclaredField("orderNumber");
        field.setAccessible(true);
        field.set(testOrderItem, 15L);
        Long orderNumber = testOrderItem.getOrderNumber();
        assertEquals(15L, orderNumber, 15L);
    }

    @Test
    public void setOrderTotalPrice() throws NoSuchFieldException, IllegalAccessException {
        final Field field = testOrderItem.getClass().getDeclaredField("orderTotalPrice");
        field.setAccessible(true);
        field.set(testOrderItem, 15.99);
        Double orderTotalPrice = testOrderItem.getOrderTotalPrice();
        assertEquals(15.99, orderTotalPrice, 15.99);
    }

    @Test
    public void setShippingAddress() throws NoSuchFieldException, IllegalAccessException {
        OrderAddressToDisplay mockAddress = Mockito.mock(OrderAddressToDisplay.class);
        final Field field = testOrderItem.getClass().getDeclaredField("shippingAddress");
        field.setAccessible(true);
        field.set(testOrderItem, mockAddress);
        OrderAddressToDisplay shippingAddress = testOrderItem.getShippingAddress();
        assertEquals("", mockAddress, shippingAddress);
    }

    @Test
    public void setLineItemsToDisplay() throws NoSuchFieldException, IllegalAccessException {
        OrderLineItemToDisplay mockLineItems = Mockito.mock(OrderLineItemToDisplay.class);
        OrderLineItemToDisplay mockLineItems2 = Mockito.mock(OrderLineItemToDisplay.class);
        OrderLineItemToDisplay mockLineItems3 = Mockito.mock(OrderLineItemToDisplay.class);
        List<OrderLineItemToDisplay> mockList = new ArrayList<>();

        mockList.add(mockLineItems);
        mockList.add(mockLineItems2);
        mockList.add(mockLineItems3);

        final Field field = testOrderItem.getClass().getDeclaredField("orderLineItemsList");
        field.setAccessible(true);
        field.set(testOrderItem, mockList);
        List<OrderLineItemToDisplay> lineItemsToDisplay = testOrderItem.getOrderLineItemsList();
        assertEquals("", mockList, lineItemsToDisplay);
    }

    @Test
    public void setOrderShipmentsToDisplayList() throws NoSuchFieldException, IllegalAccessException {
        OrderShipmentsToDisplay mockOrderShipments = Mockito.mock(OrderShipmentsToDisplay.class);
        OrderShipmentsToDisplay mockOrderShipments2 = Mockito.mock(OrderShipmentsToDisplay.class);
        OrderShipmentsToDisplay mockOrderShipments3 = Mockito.mock(OrderShipmentsToDisplay.class);
        List<OrderShipmentsToDisplay> mockList = new ArrayList<>();

        mockList.add(mockOrderShipments);
        mockList.add(mockOrderShipments2);
        mockList.add(mockOrderShipments3);

        final Field field = testOrderItem.getClass().getDeclaredField("orderShipmentsList");
        field.setAccessible(true);
        field.set(testOrderItem, mockList);
        List<OrderShipmentsToDisplay> orderShipmentsToDisplayList = testOrderItem.getOrderShipmentsList();
        assertEquals("", mockList, orderShipmentsToDisplayList);
    }
}