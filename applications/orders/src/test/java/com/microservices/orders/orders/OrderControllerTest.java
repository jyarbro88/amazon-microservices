package com.microservices.orders.orders;

import com.microservices.orders.models.display.OrderToDisplay;
import com.microservices.orders.lineItems.LineItemRepository;
import com.microservices.orders.models.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderControllerTest {

    @Mock
    private TestRestTemplate restTemplate;

    @Mock
    private LineItemRepository lineItemRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderController orderController;

    @Autowired
    private WebApplicationContext applicationContext;

    private MockMvc mockMvc;

    @Before
    public void initTests(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
    }

    @Test
    public void shouldHaveEmptyDataBaseOnLoad() throws Exception {
        mockMvc.perform(get("/orders/accountLookup?accountId=1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void getOrderDetailsForId() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date newDate = dateFormat.parse("05-12-2018");
        Order order = new Order(1L, newDate, 1L, 2L, 20.23);

        when(orderRepository.save(order)).thenReturn(order);
    }

    @Test
    public void shouldGetOrderToDisplay() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date newDate = dateFormat.parse("05-12-2018");

        OrderToDisplay testOrderToDisplay = Mockito.mock(OrderToDisplay.class);
        Order testOrder = Mockito.mock(Order.class);
//        Order testOrder = new Order(1L, newDate, 1L, 2L, 20.23);

        orderRepository.save(testOrder);

        mockMvc.perform(get("/orders/details/" + testOrder.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

//        when(orderRepository.save(testOrder)).thenReturn(testOrder);
//
//        verify(testOrder).getId().equals(testOrder.getId());

//        testOrderToDisplay.setOrderNumber(testOrder.getId());
//
//        verify(testOrderToDisplay).getOrderNumber().equals(testOrder.getId());
//        orderController.getOrderDetailsForId(testOrder.getId());

//        verify(testOrderToDisplay).getOrderNumber().equals(testOrder.getId());

//        verify(orderController).getOrderDetailsForId(testOrder.getId());
//        when(orderController.getOrderDetailsForId(testOrder.getId())).thenReturn((OrderToDisplay) status().isOk());


//        mockMvc.perform(get("/orders/details/1").accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is((Integer) id)))



    }

}