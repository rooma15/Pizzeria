package by.lobovich.delivery.service;

import by.lobovich.delivery.entity.BusketItem;
import by.lobovich.delivery.entity.Order;
import by.lobovich.delivery.entity.User;

import java.util.List;

public interface OrderService {
    Order getById(Long id);

    Order createOrder(List<BusketItem> busketItems, User user);

    void delete(Long id);

    Double getTotalPrice(Order order);

    List<Order> getAllByUser(User user);

    Order getLastByUser(User user);

}
