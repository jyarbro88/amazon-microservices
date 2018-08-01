package com.microservices.orders.orders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.orders.repositories.LineItemRepository;
import com.microservices.orders.models.Order;
import com.microservices.orders.repositories.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private LineItemRepository lineItemRepository;

    private JacksonTester<Order> jsonOrder;

    @MockBean
    private OrderController orderController;

    @Before
    public void setup(){
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void testOrderDetailsForId() throws Exception {

//        List<LineItem> lineItemList = new ArrayList<>();
//        List<Order> orderList = new ArrayList<>();

//        LineItem lineItem = new LineItem(1L, 1L, 1L, 4, 4.00, 16.00);
//        LineItem lineItem2 = new LineItem(2L, 1L, 1L, 3, 2.00, 6.00);
//        lineItem.setProductId(1L);
//        lineItem2.setProductId(2L);
//        lineItemList.add(lineItem);
//        lineItemList.add(lineItem2);
//        lineItemRepository.save(lineItem);
//        lineItemRepository.save(lineItem2);
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
//        Date newDate = dateFormat.parse("05-12-2018");
//        Order order = new Order(1L, newDate, 1L, 2L, 20.23, lineItemList);
//
//        List<Order> orderList = Collections.singletonList(order);

//        orderList.add(order);
//        orderRepository.save(order);

        // given

//        given(orderRepository.findAllById(orderList.get(0).getId())).willReturn(orderList);
//
//        MockHttpServletResponse response = mockMvc.perform(get("http://localhost:8082/orders")
//                .accept(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();

        mockMvc.perform(get("http://localhost:8082/orders").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());


        // then
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//        assertThat(response.getContentAsString()).isEqualTo(
//                jsonOrder.write(order).getJson()
//        );
    }


//    @InjectMocks
//    private OrderController orderController;

//    @Autowired
//    private WebApplicationContext applicationContext;

//    private MockMvc mockMvc;

//    @Before
//    public void initTests(){
//        MockitoAnnotations.initMocks(this);
//        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
//    }

//    @Test
//    public void shouldHaveEmptyDataBaseOnLoad() throws Exception {
//        mockMvc.perform(get("/orders/accountLookup?accountId=1")
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(0)));
//    }

    @Test
    public void getOrderDetailsForId() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date newDate = dateFormat.parse("05-12-2018");
        Order order = new Order(1L, newDate, 1L, 2L, 20.23);

        when(orderRepository.save(order)).thenReturn(order);
    }







//
//    @Test
//    public void shouldGetOrderToDisplay() throws Exception {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
//        Date newDate = dateFormat.parse("05-12-2018");
//
//        OrderToDisplay testOrderToDisplay = Mockito.mock(OrderToDisplay.class);
//        Order testOrder = Mockito.mock(Order.class);
////        Order testOrder = new Order(1L, newDate, 1L, 2L, 20.23);
//
//        orderRepository.save(testOrder);
//
//        mockMvc.perform(get("/orders/details/" + testOrder.getId())
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)));
//
//    }

}