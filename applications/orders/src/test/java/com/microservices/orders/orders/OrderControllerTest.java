package com.microservices.orders.orders;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import javax.security.auth.login.AccountException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class OrderControllerTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private Order order;

    @InjectMocks
    private OrderController orderController;

    @Test
    public void testGettingAnOrder(){


    }

}