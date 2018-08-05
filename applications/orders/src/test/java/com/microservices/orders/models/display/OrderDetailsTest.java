package com.microservices.orders.models.display;

import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class OrderDetailsTest {

    private final OrderDetails testOrderItem = new OrderDetails();

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
        OrderAddress mockAddress = Mockito.mock(OrderAddress.class);
        final Field field = testOrderItem.getClass().getDeclaredField("shippingAddress");
        field.setAccessible(true);
        field.set(testOrderItem, mockAddress);
        OrderAddress shippingAddress = testOrderItem.getShippingAddress();
        assertEquals("", mockAddress, shippingAddress);
    }

    @Test
    public void setLineItemsToDisplay() throws NoSuchFieldException, IllegalAccessException {
        OrderLineItem mockLineItems = Mockito.mock(OrderLineItem.class);
        OrderLineItem mockLineItems2 = Mockito.mock(OrderLineItem.class);
        OrderLineItem mockLineItems3 = Mockito.mock(OrderLineItem.class);
        List<OrderLineItem> mockList = new ArrayList<>();

        mockList.add(mockLineItems);
        mockList.add(mockLineItems2);
        mockList.add(mockLineItems3);

        final Field field = testOrderItem.getClass().getDeclaredField("orderLineItemsList");
        field.setAccessible(true);
        field.set(testOrderItem, mockList);
        List<OrderLineItem> lineItemsToDisplay = testOrderItem.getOrderLineItemsList();
        assertEquals("", mockList, lineItemsToDisplay);
    }

    @Test
    public void setOrderShipmentsToDisplayList() throws NoSuchFieldException, IllegalAccessException {
        OrderShipments mockOrderShipments = Mockito.mock(OrderShipments.class);
        OrderShipments mockOrderShipments2 = Mockito.mock(OrderShipments.class);
        OrderShipments mockOrderShipments3 = Mockito.mock(OrderShipments.class);
        List<OrderShipments> mockList = new ArrayList<>();

        mockList.add(mockOrderShipments);
        mockList.add(mockOrderShipments2);
        mockList.add(mockOrderShipments3);

        final Field field = testOrderItem.getClass().getDeclaredField("orderShipmentsList");
        field.setAccessible(true);
        field.set(testOrderItem, mockList);
        List<OrderShipments> orderShipmentsList = testOrderItem.getOrderShipmentsList();
        assertEquals("", mockList, orderShipmentsList);
    }
}