package by.lobovich.delivery.service.impl;

import by.lobovich.delivery.BaseTest;
import by.lobovich.delivery.entity.Order;
import by.lobovich.delivery.entity.User;
import by.lobovich.delivery.service.OrderService;
import by.lobovich.delivery.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest extends BaseTest {

    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;

    @Test
    void getById() {
        long expectedId = 1L;
        Order order = orderService.getById(expectedId);
        assertNotNull(order);
        assertEquals(expectedId, order.getId());
    }

    @Test
    void save() {
        Order order = Order.builder()
                .dateTime(LocalDateTime.now())
                .dishes(new ArrayList<>(Collections.emptyList()))
                .user(new User())
                .build();
        assertNull(order.getId());
        orderService.save(order);
        assertEquals(5L, order.getId());
    }

    @Test
    void delete() {
        long id = 4L;
        orderService.delete(id);
        Throwable thrown = Assert.assertThrows(JpaObjectRetrievalFailureException.class, () -> orderService.getById(id));
        String message = thrown.getMessage();
        System.out.println(message);
    }

    @Test
    void getTotalPrice() {
        Order order = orderService.getById(1L);
        BigDecimal exceptionTotalPrice = BigDecimal.valueOf(37.15);
        BigDecimal totalPrice = orderService.getTotalPrice(order);
        assertEquals(exceptionTotalPrice, totalPrice);
    }

    @Test
    void getAllByUser() {
        User user = userService.getById(1L);
        List<Order> allByUser = orderService.getAllByUser(user);
        assertEquals(2, allByUser.size());
    }

    @Test
    void getLastByUser() {
        User user = userService.getById(1L);
        Order order = orderService.getLastByUser(user);
        assertEquals(4L, order.getId());
    }
}