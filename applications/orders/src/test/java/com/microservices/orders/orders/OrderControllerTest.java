package com.microservices.orders.orders;

import com.microservices.orders.controllers.OrderController;
import com.microservices.orders.services.Order.OrderService;
import org.apache.catalina.security.SecurityConfig;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
@ContextConfiguration(classes = SecurityConfig.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private OrderService orderService;
    @InjectMocks
    private OrderController orderController;

    private JSONObject testJsonOrderObject = new JSONObject();
    private JSONObject testJsonLineItemsObject = new JSONObject();

    @Before
    public void setup() throws JSONException, ParseException {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.orderController).build();

        testJsonLineItemsObject.put("productId", 1L);
        testJsonLineItemsObject.put("shipmentId", 1L);
        testJsonLineItemsObject.put("orderId", 1L);
        testJsonLineItemsObject.put("quantity", 3);
        testJsonLineItemsObject.put("singleItemPrice", 3.00);
        testJsonLineItemsObject.put("totalPrice", 9.00);


        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date testDate = dateFormat.parse("05-12-2018");

        testJsonOrderObject.put("productId", 1L);
        testJsonOrderObject.put("orderDate", testDate);
        testJsonOrderObject.put("shippingAddressId", 1L);
        testJsonOrderObject.put("billingAddressId", 2L);
        testJsonOrderObject.put("totalPrice", 20.23);
        testJsonOrderObject.put("lineItems", testJsonLineItemsObject);
    }

    @Test
    public void testGetAllOrders() throws Exception {
        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetAllOrdersForAccountId() throws Exception {
        mockMvc.perform(get("/orders/accountLookup?accountId=1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testCreateNewOrder() throws Exception {
        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(testJsonOrderObject)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void testUpdateOrder() throws Exception {
        mockMvc.perform(put("/orders/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(testJsonOrderObject)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testDeleteOrder() throws Exception {
        mockMvc.perform(delete("/orders/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }


}